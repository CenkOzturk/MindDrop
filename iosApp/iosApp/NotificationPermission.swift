//
// Created by Cenk Öztürk on 4.10.2026.
//

import UserNotifications

func requestNotificationPermission() {
    UNUserNotificationCenter.current().requestAuthorization(
        options: [.alert, .sound, .badge]
    ) { granted, error in
        if let error = error {
            print("Notification permission error: \(error)")
        } else {
            print("Notification permission granted: \(granted)")
        }
    }
}
