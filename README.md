<div align="center">

  <h1>Frost View</h1>

  <p>
    A community-maintained fork of Nuvio Mobile, rebranded as Frost View.
    <br />
    Keeps the original app experience, dark interface, playback features, and integrations while building independently under the Frost View name.
  </p>

  <p><strong>Based on <a href="https://github.com/NuvioMedia/NuvioMobile">Nuvio Mobile</a> · Licensed under GPL-3.0</strong></p>

</div>

## Get Frost View

Release and installation links will be published from this repository as Frost View builds become available.

## Build from source

```bash
git clone https://github.com/Valthix/Frost-View.git
cd Frost-View
```

### Android

Android development requires Android Studio and the Android SDK.

```bash
./gradlew :androidApp:assembleFullDebug
```

### iOS

iOS development requires macOS and Xcode.

```bash
env NUVIO_IOS_DISTRIBUTION=full xcodebuild \
  -project iosApp/iosApp.xcodeproj \\
  -scheme iosApp \\
  -configuration Debug \\
  -sdk iphonesimulator \\
  -derivedDataPath build/ios-derived-full-simulator \\
  CODE_SIGNING_ALLOWED=NO \\
  build
```

The shared app is built with Kotlin Multiplatform and Compose Multiplatform.

## Upstream and compatibility

Frost View retains upstream Nuvio identifiers where required for package compatibility, authentication callbacks, existing integrations, and data continuity. Those identifiers are intentionally not renamed as part of the visual rebrand.

## License

[GNU General Public License v3.0](./LICENSE)
