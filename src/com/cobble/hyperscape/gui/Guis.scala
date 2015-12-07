package com.cobble.hyperscape.gui

import com.cobble.hyperscape.reference.Reference
import com.cobble.hyperscape.render.Render

object Guis {

    Render.mainCamera.mode = Reference.Camera.ORTHOGRAPHIC_MODE
    Render.mainCamera.updatePerspective()

    val guiMainMenu = new GuiMainMenu

    val guiOptions = new GuiOptions
}
