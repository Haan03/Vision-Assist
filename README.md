# Vision Assist: YOLOv8 Live Object Detection Android App

## Description
Vision Assist is an Android application designed to perform real-time object detection using the YOLOv8 (You Only Look Once version 8) machine learning model. By leveraging Android's CameraX and TensorFlow Lite, the app identifies objects in the camera feed and provides immediate, context-aware audio feedback via Text-to-Speech (TTS), making it a potentially valuable tool for visually impaired users.

## Features
- **Real-Time Object Detection**: Uses the fast and efficient YOLOv8 model via TensorFlow Lite.
- **Dynamic Audio Feedback**: Uses Text-to-Speech to announce detected objects. It intelligently calculates the object's relative distance ("Very Close", "Near", "Far") and horizontal position ("Left", "Center", "Right").
- **Adaptive Alerting**: The app dynamically adjusts its speaking frequency. Objects that are "Very Close" trigger faster audio alerts compared to objects further away.
- **Performance Overlay**: Displays bounding boxes, class names, and the model's inference time directly on the live camera feed.

## Architecture Overview
The application's data flow is structured as follows:

1.  **Image Capture (CameraX)**: The `MainActivity` binds to the device's camera using CameraX. It captures frames and converts them into an `ARGB_8888` Bitmap format suitable for processing.
2.  **Object Detection (TensorFlow Lite)**:
    *   The `Detector` class takes the Bitmap, resizes it, normalizes the pixels, and feeds it into the YOLOv8 TensorFlow Lite interpreter.
    *   The model outputs a tensor containing bounding box coordinates and confidence scores for various classes.
3.  **Post-Processing (NMS)**:
    *   The raw output is filtered by a `CONFIDENCE_THRESHOLD`.
    *   Non-Maximum Suppression (NMS) is applied using an `IOU_THRESHOLD` to remove duplicate overlapping boxes for the same object, ensuring only the most confident detection is kept.
4.  **UI Updates (OverlayView)**: The filtered bounding boxes and the total inference time are sent to the `OverlayView`, which draws them on top of the camera preview.
5.  **Audio Feedback (Text-to-Speech)**: The most confident detection in the current frame is evaluated. Its area determines distance, and its center X-coordinate determines position. This information is formatted into a string and spoken aloud using Android's `TextToSpeech` engine.

## Getting Started

### Prerequisites
- Android Studio (latest version recommended)
- An Android device running SDK 26 (Android 8.0) or higher.
- A YOLOv8 TensorFlow Lite model (`.tflite`).

### Setup Instructions
1.  **Clone the Repository**:
    ```bash
    git clone https://github.com/abhay8844/VisionAssist.git
    cd VisionAssist
    ```

2.  **Add Your Model**:
    *   Place your YOLOv8 `.tflite` model inside the `app/src/main/assets/` folder.
    *   If you have a custom labels file, place it in the same `assets/` directory.

3.  **Configure Constants**:
    *   Open `app/src/main/java/com/abhay/visionassist/Constants.kt`.
    *   Ensure `MODEL_PATH` exactly matches the filename of your model (e.g., `"yolov8n.tflite"`).
    *   Ensure `LABELS_PATH` matches your labels file, or comment it out if your model includes embedded metadata.
    *   You can also adjust detection thresholds (`CONFIDENCE_THRESHOLD`, `IOU_THRESHOLD`) and audio feedback intervals here.

4.  **Build and Run**:
    *   Open the project in Android Studio.
    *   Sync the project with Gradle files.
    *   Build and run the application on a physical Android device (emulators may not support the necessary camera hardware or run the model efficiently).

## Recent Upgrades
- **Centralized Configuration**: Moved critical thresholds (Confidence, IoU) and audio settings to `Constants.kt` for easier tuning.
- **Dynamic TTS Intervals**: The audio feedback rate now scales with the proximity of the detected object, providing more urgent alerts for closer objects.
- **Inference Time Display**: The `OverlayView` now actively renders the model's inference time (in milliseconds) on the screen, aiding in performance profiling and model selection.

## Future Improvements
- **Background Processing**: Allow the app to run and provide audio feedback even when minimized or when the screen is off.
- **Model Selection UI**: Add a settings menu allowing users to choose between different YOLOv8 model sizes (nano, small, medium) dynamically based on their device's capabilities.
- **Haptic Feedback**: Integrate vibration patterns corresponding to object distance or specific object classes (e.g., sharp vibration for obstacles).

## Contributing
Contributions are welcome! If you want to contribute to this project, feel free to fork the repository and submit a pull request with your changes.

## Contact
For any questions or feedback, feel free to open an issue in the repository, or contact the provider on [GitHub](https://github.com/abhay8844).
