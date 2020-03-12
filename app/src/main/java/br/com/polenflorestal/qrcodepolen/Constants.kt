package br.com.polenflorestal.qrcodepolen

// sharedPreferences nome
const val SP_NOME = "qrcodepolen_prefs"
const val SP_KEY_VERSION_CODE = "version_code"

// defaults values (quando nao encontra o valor pedido)
const val DEFAULT_STRING_VALUE = "-1"
const val DEFAULT_LONG_VALUE : Long = 0
const val DEFAULT_INT_VALUE : Int = 0

const val QR_DATA = "data"
const val QR_IS_TOOLBAR_SHOW = "toolbar_show"
const val QR_TOOLBAR_DRAWABLE_ID = "toolbar_drawable_id"
const val QR_TOOLBAR_TEXT = "toolbar_text"
const val QR_TOOLBAR_BACKGROUND_COLOR = "toolbar_background_color"
const val QR_TOOLBAR_TEXT_COLOR = "toolbar_text_color"
const val QR_CAMERA_MARGIN_LEFT = "camera_margin_left"
const val QR_CAMERA_MARGIN_TOP = "camera_margin_top"
const val QR_CAMERA_MARGIN_RIGHT = "camera_margin_right"
const val QR_CAMERA_MARGIN_BOTTOM = "camera_margin_bottom"
const val QR_BACKGROUND_COLOR = "background_color"
const val QR_CAMERA_BORDER = "CAMERA_BORDER"
const val QR_CAMERA_BORDER_COLOR = "CAMERA_BORDER_COLOR"
const val QR_IS_SCAN_BAR = "IS_SCAN_BAR"
const val QR_IS_BEEP = "IS_BEEP"
const val QR_BEEP_RESOURCE_ID = "BEEP_RESOURCE_ID"
const val QR_QR_SCANNER_REQUEST = 1