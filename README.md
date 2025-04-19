
# MyActivityTracker - Goals Tracker Feature

## Overview
MyActivityTracker is a lightweight, modular Android app for tracking running activity and helping users stay on top of their weekly workout goals. Built on top of the Android Essentials Course App by Philipp Lackner, this project extends the original running tracker with a custom Goals Tracker feature, designed and implemented independently to explore real-world Android development patterns.
The app combines intuitive user flows with clean architecture and background processing — with a focus on scalable structure, offline-first design, and meaningful user feedback through smart notifications.

## Features Overview
The goal of this feature is to help users stay consistent with their weekly workouts. It provides a lightweight goal-setting system, progress tracking, and smart nudges to encourage healthy habits — all seamlessly integrated with the original run tracking flow.

### Main Goals:
- Help users reach their weekly workout goals.
- Track workouts (currently only running).
- Show progress through a visual tracker.
- Send smart reminders to stay on track with goals.

## 🛠 Technical Overview

This project showcases a scalable, modular Android architecture designed to handle real-world challenges like foreground services, permission-heavy features (e.g. GPS), notification scheduling, and efficient offline-first data handling — all in a clean, testable, and user-responsive way.

### 🔧 Architecture & Modularity
- Built with a multi-module hybrid structure: feature-based modules split by layer (domain, data, presentation)
- Shared modules (`core`, `build-logic`, etc.) define base architecture, custom Gradle plugins, and common utilities (e.g., database, repository interfaces), promoting consistency and reusability across all layers.
- Foreground service flows are integrated cleanly through architectural boundaries — avoiding UI-layer leaks and maintaining a clear separation between app logic and service infrastructure.
- Designed for high modularity and scalability — ideal for long-term growth or team-based work

### 🔄 Offline-First Sync & Background Work
- Local-first design using Room ensures consistent and reliable local data storage.
- Server sync handled via WorkManager, scheduled at ideal idle/wakeup moments
- Ensures user data is always in sync across devices with minimal battery/network cost

### 🛰 Foreground Services & GPS Tracking
- Built-in foreground service handling for continuous GPS tracking
- Full support for runtime permission flow, notification visibility, and lifecycle stability — both in-app and background
- GPS tracking works smoothly in active sessions and background, even during edge cases like app restarts

### 🔔 Smart Reminders & Notification UX
- Daily notifications to nudge users based on their weekly progress
- Interactive notification actions (e.g., Remind Me Later) integrated into clean Compose dialogs
- Works fully offline and with cached data — designed for minimal friction and high reliability


## Conclusion
This project demonstrates the ability to work with modular architecture, background work, and permission-heavy tasks like GPS tracking, while focusing on smooth user experience and offline functionality.
