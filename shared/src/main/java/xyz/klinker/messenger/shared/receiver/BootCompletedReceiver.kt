/*
 * Copyright (C) 2017 Luke Klinker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.klinker.messenger.shared.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import xyz.klinker.messenger.api.implementation.firebase.ScheduledTokenRefreshService
import xyz.klinker.messenger.shared.service.jobs.CleanupOldMessagesJob
import xyz.klinker.messenger.shared.service.jobs.ContactSyncJob
import xyz.klinker.messenger.shared.service.jobs.ScheduledMessageJob
import xyz.klinker.messenger.shared.service.jobs.SignoutJob
import xyz.klinker.messenger.shared.service.jobs.SubscriptionExpirationCheckJob

/**
 * Receiver for when boot has completed. This will be responsible for starting up the content
 * observer service and scheduling any messages that are to be sent in the future.
 */
class BootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        try {
            if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
                ScheduledMessageJob.scheduleNextRun(context)
                CleanupOldMessagesJob.scheduleNextRun(context)
                ContactSyncJob.scheduleNextRun(context)
                SubscriptionExpirationCheckJob.scheduleNextRun(context)
                SignoutJob.scheduleNextRun(context)
                ScheduledTokenRefreshService.scheduleNextRun(context)
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

    }

}