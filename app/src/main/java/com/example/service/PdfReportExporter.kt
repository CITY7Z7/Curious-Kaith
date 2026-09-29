package com.example.service

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import com.example.data.model.DictionaryWord
import com.example.data.model.StudyPlan
import com.example.data.model.UserProgress
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PdfReportExporter {

    fun generateAndShareReport(
        context: Context,
        userProgress: UserProgress,
        studyPlan: StudyPlan?,
        words: List<DictionaryWord>
    ): File? {
        val document = PdfDocument()
        val pageWidth = 595 // A4 standard width in pt
        val pageHeight = 842 // A4 standard height in pt

        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas

        // Background
        val bgPaint = Paint().apply { color = Color.rgb(250, 250, 252) }
        canvas.drawRect(0f, 0f, pageWidth.toFloat(), pageHeight.toFloat(), bgPaint)

        // Header Banner
        val headerPaint = Paint().apply { color = Color.rgb(30, 64, 175) }
        canvas.drawRect(0f, 0f, pageWidth.toFloat(), 110f, headerPaint)

        // Gold Accent Line
        val goldLinePaint = Paint().apply {
            color = Color.rgb(217, 119, 6)
            strokeWidth = 4f
        }
        canvas.drawLine(0f, 110f, pageWidth.toFloat(), 110f, goldLinePaint)

        // Title text
        val titlePaint = Paint().apply {
            color = Color.WHITE
            textSize = 22f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }
        canvas.drawText("ОТЧЁТ ОБ ОБУЧЕНИИ РУССКОМУ ЯЗЫКУ", 36f, 50f, titlePaint)

        val subtitlePaint = Paint().apply {
            color = Color.rgb(224, 231, 255)
            textSize = 13f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            isAntiAlias = true
        }
        canvas.drawText("Школа полного погружения «Говорун» (Метод Попугая)", 36f, 75f, subtitlePaint)

        val dateStr = SimpleDateFormat("dd MMMM yyyy г., HH:mm", Locale("ru")).format(Date())
        val datePaint = Paint().apply {
            color = Color.rgb(199, 210, 254)
            textSize = 10f
            isAntiAlias = true
        }
        canvas.drawText("Дата формирования: $dateStr", 36f, 95f, datePaint)

        // User stats card
        var curY = 135f
        drawCard(canvas, 36f, curY, (pageWidth - 72).toFloat(), 95f, Color.WHITE)

        val labelPaint = Paint().apply {
            color = Color.rgb(71, 85, 105)
            textSize = 11f
            isAntiAlias = true
        }
        val valuePaint = Paint().apply {
            color = Color.rgb(15, 23, 42)
            textSize = 15f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        canvas.drawText("Текущий статус ученика:", 50f, curY + 25f, labelPaint)
        canvas.drawText(userProgress.levelName, 50f, curY + 45f, valuePaint)

        // Column 1: XP
        canvas.drawText("Опыт (XP):", 50f, curY + 70f, labelPaint)
        canvas.drawText("${userProgress.xp} очков", 50f, curY + 86f, valuePaint)

        // Column 2: Streak
        canvas.drawText("Ударный режим:", 190f, curY + 70f, labelPaint)
        canvas.drawText("${userProgress.currentStreak} дн. подряд", 190f, curY + 86f, valuePaint)

        // Column 3: Words
        val learnedWords = words.filter { it.isMastered || it.repetitionCount > 0 }.size
        canvas.drawText("Слов в практике:", 340f, curY + 70f, labelPaint)
        canvas.drawText("$learnedWords слов", 340f, curY + 86f, valuePaint)

        // Individual Study Plan Section
        curY += 115f
        val sectionHeaderPaint = Paint().apply {
            color = Color.rgb(30, 58, 138)
            textSize = 14f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }
        canvas.drawText("ИНДИВИДУАЛЬНЫЙ УЧЕБНЫЙ ПЛАН", 36f, curY, sectionHeaderPaint)

        curY += 10f
        drawCard(canvas, 36f, curY, (pageWidth - 72).toFloat(), 75f, Color.WHITE)

        val minutesGoal = studyPlan?.dailyMinutesGoal ?: 15
        val wordsGoal = studyPlan?.dailyWordsGoal ?: 10
        val focus = studyPlan?.focusArea ?: "Все разделы"
        val reminderTime = if (studyPlan?.isReminderEnabled == true) {
            String.format(Locale.getDefault(), "%02d:%02d", studyPlan.reminderHour, studyPlan.reminderMinute)
        } else {
            "Отключено"
        }

        canvas.drawText("Цель в день: $minutesGoal минут занятий", 50f, curY + 26f, labelPaint)
        canvas.drawText("Цель по лексике: $wordsGoal новых слов/повторов", 50f, curY + 46f, labelPaint)
        canvas.drawText("Приоритетное направление: $focus", 290f, curY + 26f, labelPaint)
        canvas.drawText("Ежедневное напоминание: $reminderTime", 290f, curY + 46f, labelPaint)

        // Words Table Section
        curY += 100f
        canvas.drawText("ОСВОЕННАЯ ЛЕКСИКА И ПРИМЕРЫ В КОНТЕКСТЕ", 36f, curY, sectionHeaderPaint)

        curY += 12f
        drawCard(canvas, 36f, curY, (pageWidth - 72).toFloat(), 290f, Color.WHITE)

        // Table Header
        val tableHeaderPaint = Paint().apply {
            color = Color.rgb(100, 116, 139)
            textSize = 10f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }
        val dividerPaint = Paint().apply {
            color = Color.rgb(226, 232, 240)
            strokeWidth = 1f
        }

        var tableY = curY + 22f
        canvas.drawText("СЛОВО", 50f, tableY, tableHeaderPaint)
        canvas.drawText("ЧАСТЬ РЕЧИ", 150f, tableY, tableHeaderPaint)
        canvas.drawText("ПРИМЕР В КОНТЕКСТЕ", 260f, tableY, tableHeaderPaint)

        tableY += 8f
        canvas.drawLine(44f, tableY, (pageWidth - 44).toFloat(), tableY, dividerPaint)

        val wordRowPaint = Paint().apply {
            color = Color.rgb(30, 41, 59)
            textSize = 11f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }
        val contextRowPaint = Paint().apply {
            color = Color.rgb(51, 65, 85)
            textSize = 10f
            isAntiAlias = true
        }

        val displayWords = words.take(8)
        for (w in displayWords) {
            tableY += 28f
            canvas.drawText(w.word, 50f, tableY, wordRowPaint)
            canvas.drawText(w.partOfSpeech, 150f, tableY, contextRowPaint)

            val shortContext = if (w.exampleSentence.length > 42) {
                w.exampleSentence.take(40) + "..."
            } else {
                w.exampleSentence
            }
            canvas.drawText(shortContext, 260f, tableY, contextRowPaint)
            canvas.drawLine(44f, tableY + 6f, (pageWidth - 44).toFloat(), tableY + 6f, dividerPaint)
        }

        // Stamp and Signature
        curY += 310f
        drawCard(canvas, 36f, curY, (pageWidth - 72).toFloat(), 70f, Color.rgb(241, 245, 249))

        val stampBorderPaint = Paint().apply {
            color = Color.rgb(5, 150, 105)
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }
        val stampTextPaint = Paint().apply {
            color = Color.rgb(5, 150, 105)
            textSize = 11f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        val stampRect = RectF(50f, curY + 12f, 180f, curY + 58f)
        canvas.drawRoundRect(stampRect, 8f, 8f, stampBorderPaint)
        canvas.drawText("✓ ПРОВЕРЕНО", 65f, curY + 32f, stampTextPaint)
        canvas.drawText("МЕТОД ПОПУГАЯ", 65f, curY + 48f, stampTextPaint)

        canvas.drawText("Интерактивный тренер: Попугай Кеша 🦜", 210f, curY + 30f, wordRowPaint)
        canvas.drawText("«Услышал — повтори! Только чистая русская речь.»", 210f, curY + 48f, labelPaint)

        document.finishPage(page)

        // Save PDF to cache/reports
        val reportsDir = File(context.cacheDir, "reports")
        if (!reportsDir.exists()) reportsDir.mkdirs()

        val pdfFile = File(reportsDir, "otchet_russkiy_yazyk.pdf")
        try {
            val fos = FileOutputStream(pdfFile)
            document.writeTo(fos)
            fos.close()
            document.close()
            return pdfFile
        } catch (e: Exception) {
            e.printStackTrace()
            document.close()
            return null
        }
    }

    fun sharePdfReport(context: Context, pdfFile: File) {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            pdfFile
        )
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "Отчёт об обучении русскому языку — Говорун")
            putExtra(Intent.EXTRA_TEXT, "Мой прогресс в изучении русского языка по методу попугая!")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Экспортировать PDF-отчёт"))
    }

    private fun drawCard(canvas: Canvas, x: Float, y: Float, width: Float, height: Float, bgColor: Int) {
        val rect = RectF(x, y, x + width, y + height)
        val fillPaint = Paint().apply {
            color = bgColor
            isAntiAlias = true
        }
        val borderPaint = Paint().apply {
            color = Color.rgb(226, 232, 240)
            style = Paint.Style.STROKE
            strokeWidth = 1.5f
            isAntiAlias = true
        }
        canvas.drawRoundRect(rect, 12f, 12f, fillPaint)
        canvas.drawRoundRect(rect, 12f, 12f, borderPaint)
    }
}
