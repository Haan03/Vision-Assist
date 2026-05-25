package com.abhay.visionassist

object Constants {
    const val MODEL_PATH = "yolov8n.tflite" // Must match exactly!
    const val LABELS_PATH = "labels.txt"
    // val LABELS_PATH: String? = null

    // Thresholds for detection
    const val CONFIDENCE_THRESHOLD = 0.3F
    const val IOU_THRESHOLD = 0.5F

    // Audio feedback configuration
    const val MIN_CONFIDENCE_FOR_SPEECH = 0.60F
    const val AUDIO_FEEDBACK_INTERVAL_NORMAL = 3000L // 3 seconds
    const val AUDIO_FEEDBACK_INTERVAL_CLOSE = 1500L  // 1.5 seconds
}
