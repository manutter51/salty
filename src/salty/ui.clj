(ns salty.ui
  "User interactions"
  (:use [salty.find :only [as-select]]))

(defn type-into
  "Type the into the given input field."
  [fld s]
  (.sendKey fld (into-array [s])))

(defn click-on
  "Click the given button, checkbox, radio button, etc."
  [btn]
  (.click btn))

(defn select-by-label
  "Given a <select> input, select the option with the
given visible text."
  [elem label]
  (.selectByVisibleText (as-select elem) label))

(defn deselect-all
  "Given a <select> input, make sure none of the options
are selected."
  [elem]
  (.deselectAll (as-select elem)))



(defn submit
  "Given any element from inside a form, submit the
enclosing form."
  [elem]
  (.submit elem))

(defn use-window
  "Bring the window to the front, and direct all subsequent
commands to the contents of this window."
  [driver name-or-handle]
  (.window (.switchTo driver) name-or-handle))

(defn use-frame
  "Direct all subsequent commands into a particular frame or subframe. Frames are specified relative to *top*, and can be specified either by name or by index. Subframes can be specified by a dot-separated path, e.g. \"aframe.0.inner\" means the subframe named \"inner\" inside the first subframe inside the frame named \"aframe\" at the top level."
  [driver path-or-index]
  (.frame (.switchTo driver) path-or-index))

(defn use-alert
  "Direct all subsequent commands to a currently-open popup alert."
  [driver]
  (.alert (.switchTo driver)))

(defn history-next
  "Navigate to the next item in the browser history."
  [driver]
  (.forward (.navigate driver)))

(defn history-back
  "Navigate to the previous item in the browser history."
  [driver]
  (.forward (.navigate driver)))

