package com.cobble.hyperscape.core

import com.cobble.hyperscape.Game
import com.cobble.hyperscape.core.gamestate.{GameState, GameStates}
import com.cobble.hyperscape.reference.Reference
import com.cobble.hyperscape.registry.{EventRegistry, GameStateRegistry}
import com.cobble.hyperscape.render.{Render, Camera}
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11

//import org.lwjgl.input.{Keyboard, Mouse}
//import org.lwjgl.opengl.{Display, GL11}

class HyperScape {

    var prevMouseState: Int = -1

    def init(): Unit = {
        GameStates.registerGameStates()
        changeState(Reference.GameState.MAIN_MENU)
    }

    def changeState(newState: String): Unit = {
        if (HyperScape.currentGameState != null) HyperScape.currentGameState.destroy()
        HyperScape.currentGameState = GameStateRegistry.getGameState(newState)
        HyperScape.currentGameState.changeTo()
    }

    def tick(): Unit = {
        HyperScape.currentGameState.tick()
//        while (Keyboard.next()) {
//            val character = Keyboard.getEventCharacter
//            val characterVal = Keyboard.getEventKey
//            EventRegistry.getButtonEventListeners.foreach(eventListener => {
//                eventListener.onButtonTypingHold(character, characterVal)
//            })
//	        Keyboard.enableRepeatEvents(false)
//            if (Keyboard.getEventKeyState) {
//                EventRegistry.getButtonEventListeners.foreach(eventListener => {
//                    eventListener.onButtonDown(character, characterVal)
//                })
//
//            } else {
//                EventRegistry.getButtonEventListeners.foreach(eventListener => {
//                    eventListener.onButtonUp(character, characterVal)
//                })
//            }
//	        Keyboard.enableRepeatEvents(true)
//        }
//
//        if (Keyboard.getEventKeyState) {
//            //			println("Test")
//            val character = Keyboard.getEventCharacter
//            val characterVal = Keyboard.getEventKey
//            EventRegistry.getButtonEventListeners.foreach(eventListener => {
//                eventListener.onButtonHold(character, characterVal)
//            })
//            if (characterVal == Keyboard.KEY_ESCAPE && (Mouse.isGrabbed || Mouse.isClipMouseCoordinatesToWindow)) {
//                Mouse.setGrabbed(false)
//                Mouse.setClipMouseCoordinatesToWindow(false)
//            }
//
//
//        }
//
//        EventRegistry.getButtonEventListeners.foreach(eventListener => eventListener.onTick())
//
//        while (Mouse.next()) {
//            val x = Mouse.getX
//            val y = Mouse.getY
//            val dx = Mouse.getDX
//            val dy = Mouse.getDY
//            val mouseState = Mouse.getEventButton
//
//            if (Mouse.isGrabbed)
//                EventRegistry.getMouseListeners.foreach(eventListener => {
//                    eventListener.onMouseMove(x, y, dx, dy)
//                })
//
//            if (Mouse.getEventButtonState) {
//                if (Mouse.getEventButton != -1) {
//                    if (Mouse.isInsideWindow) {
//                        Mouse.setGrabbed(true)
//                        Mouse.setClipMouseCoordinatesToWindow(true)
//                    }
//                    if (Mouse.isGrabbed)
//                        EventRegistry.getMouseListeners.foreach(eventListener => {
//                            eventListener.onMouseDown(x, y, dx, dy, mouseState)
//                        })
//                    HyperScape.anyMouseButtonDown = true
//                }
//            } else {
//                if (Mouse.getEventButton != -1) {
//                    if (Mouse.isGrabbed)
//                        EventRegistry.getMouseListeners.foreach(eventListener => {
//                            eventListener.onMouseUp(x, y, dx, dy, mouseState)
//                        })
//                    HyperScape.anyMouseButtonDown = false
//                }
//            }
//
//        }
//
//
//        if (HyperScape.anyMouseButtonDown) {
//            val x = Mouse.getX - (Display.getWidth / 2)
//            val y = Mouse.getY - (Display.getHeight / 2)
//            val dx = Mouse.getDX
//            val dy = Mouse.getDY
//            val mouseState = Mouse.getEventButton
//            EventRegistry.getMouseListeners.foreach(eventListener => {
//                eventListener.mouseDown(x, y, dx, dy, mouseState)
//            })
//        }
//
//        if (Mouse.isGrabbed)
//            Mouse.setCursorPosition(Display.getWidth / 2, Display.getHeight / 2)
    }

    def render(): Unit = {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT)
        Render.mainCamera.mode = Reference.Camera.PERSPECTIVE_MODE
        Render.mainCamera.updatePerspective()
        Render.mainCamera.uploadPerspective()
        HyperScape.currentGameState.perspectiveRender()

        //        GL11.glDisable(GL11.GL_DEPTH_TEST)
        Render.mainCamera.mode = Reference.Camera.ORTHOGRAPHIC_MODE
        //        Render.mainCamera.fov = 160
        Render.mainCamera.updatePerspective()
        Render.mainCamera.uploadPerspective()
        HyperScape.currentGameState.orthographicRender()
        //        GL11.glEnable(GL11.GL_DEPTH_TEST)
    }

    def destroy(): Unit = {
        HyperScape.currentGameState.destroy()
    }
}

object HyperScape {
    /** The Camera that renders they game */
//    val mainCamera = new Camera
    /** The current game state that the game is in */
    var currentGameState: GameState = null
    var lines: Boolean = false
    var debug = false
    var anyMouseButtonDown = false
    var isCloseRequested: Boolean = false
    var drawFog: Boolean = true

    def requestClose(): Unit = {
        isCloseRequested = true
    }
}