package com.example.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DailyReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val reminders = listOf(
            "Пора практиковать произношение! Кеша приготовил новые слоги и слова.",
            "Не прерывай ударный темп! 5 минут повторения сделают речь увереннее.",
            "Слово дня уже ждёт тебя во встроенном словаре. Загляни!",
            "Услышал — повтори! Прокачай русский язык прямо сейчас."
        )
        val selectedMessage = reminders.random()
        NotificationHelper.showReminderNotification(
            context = context,
            title = "Время для русского языка! 🦜",
            message = selectedMessage
        )
    }
}
