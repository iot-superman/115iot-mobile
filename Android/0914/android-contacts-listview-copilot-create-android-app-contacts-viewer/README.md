# android-contacts-listview

Kotlin Android App using **ListView + BaseAdapter** to display phone contacts from `ContactsContract`, with real-time name filtering via `SearchView`.

## Features

- Runtime permission request for `READ_CONTACTS` (Android 6.0+)
- Read contact **name / phone / email** using `ContentResolver`
- Display contacts in `ListView` with a custom adapter extending `BaseAdapter`
- Adapter implements `Filterable` for instant name filtering (`contains(query, ignoreCase = true)`)
- Click any item to show `Toast` with contact name and phone

## Tech Details

- Language: Kotlin
- minSdk: 24
- targetSdk: 35
- compileSdk: 35
- Gradle: 8.10.2 (wrapper)

## Run

1. Open project in Android Studio (latest stable).
2. Let Gradle sync complete.
3. Run on emulator/device (Android 7.0+).
4. Grant Contacts permission when prompted.

## Project Structure

- `app/src/main/java/com/example/contactsapp/Contact.kt`
- `app/src/main/java/com/example/contactsapp/ContactAdapter.kt`
- `app/src/main/java/com/example/contactsapp/MainActivity.kt`
- `app/src/main/res/layout/activity_main.xml`
- `app/src/main/res/layout/item_contact.xml`
