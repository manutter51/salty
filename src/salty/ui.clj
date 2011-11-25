(ns salty.ui
  "User interactions"
  (:use [salty.find :only [as-select find-by-tag]]))

;; Forms

(defn type-into
  "Type the into the given input field."
  [fld s]
  (.sendKeys fld (into-array [s])))

(defn clear
  "Delete all text from given text input field."
  [fld]
  (.clear fld))

(defn click-on
  "Click the given button, checkbox, radio button, etc."
  [btn]
  (.click btn))

(defn select-in
  "Given a <select> input, select the option with the
given visible text. Alternately, use :index plus a (zero
based) number to select an option by its position in the
list"
  ([elem label]
     (.selectByVisibleText (as-select elem) label))
  ([elem k index]
     (if-not (= k :index)
       (throw (Exception.
               "Illegal arguments to select-in"))
       (if-let [o (nth
                (find-by-tag elem "option" :all)
                index)]
         (let [name (.getText o)]
           (select-in elem name))))))

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

;; -----

;; Windows, frames and history

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

;; -----

;; Inspecting elements

(defn get-tag
  "Returns the tag name of the element, e.g. \"input\"."
  [elem]
  (.getTagName elem))

(defn get-attr
  "Returns the current value of the given attribute of the given element."
  [elem attr-name]
  (.getAttribute elem attr-name))

(defn selected?
  "If the element is a checkbox, option, or radio button, return boolean true/false indicating whether it is checked/selected."
  [elem]
  (.isSelected elem))

(defn enabled?
  "Return boolean true/false indicating whether the given element is enabled."
  [elem]
  (.isEnabled elem))

(defn displayed?
  "Returns true if the element is displayed."
  [elem]
  (.isDisplayed elem))

(defn get-inner-text
  "Get the contents of the elements and any child elements, in text form."
  [elem]
  (.getText elem))

(defn get-location
  "Get the x-y coordinates of the top-left corner of the element. Returns a vector of [x y]."
  [elem]
  (let [p (.getLocation elem)]
    [(.x p) (.y p)]))

(defn get-top
  "Get the top coordinate of the element."
  [elem]
  (.y (.getLocation elem)))

(defn get-left
  "Get the left coordinate of the element."
  [elem]
  (.x (.getLocation elem)))

(defn get-size
  "Get the width and height of the element in pixels. Returns [width height]."
  [elem]
  (let [sz (.getSize elem)]
    [(.width sz) (.height sz)]))

(defn get-width
  "Get width of the element in pixels."
  [elem]
  (.width (.getSize elem)))

(defn get-height
  "Get the height of the element in pixels."
  [elem]
  (.height (.getSize elem)))

(defn get-css
  "Get the current value of the given CSS property."
  [elem css]
  (.getCssValue elem css))

