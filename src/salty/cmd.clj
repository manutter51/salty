(ns salty.cmd
  (:import org.openqa.selenium.interactions.Actions
           [org.openqa.selenium Keys WebDriver])
  (:refer-clojure :exclude [type]))

(def mouse-pressed? nil)

(defmacro run*
  "Execute one or more actions using the given Actions object."
  [driver & actions]
  `(-> ~driver
       ~@actions
       (.perform)))

(defmacro run
  "Execute one or more actions in the given browser."
  [driver & actions]
  `(binding [mouse-pressed? (atom false)]
     (when-not (instance? WebDriver ~driver)
       (throw (Exception. "First arg to salty.actions/do is not a valid WebDriver instance.")))
     (run* (Actions. ~driver) ~@actions)))

(defn click
  "Click on the given web element. If no web element is given, click at the current mouse location."
  ([driver]
     (.click driver))
  ([driver elem]
     (.click driver elem)))

(defn double-click
  "Double-click on the given web element. If no web element is given, double-click at the current mouse location."
  ([driver]
     (.doubleClick driver))
  ([driver elem]
     (.doubleClick driver elem)))

(defn click-and-hold
  "Click on a given web element without releasing the mouse button. If no web element is given, click and hold at the current mouse location."
  ([driver]
     (reset! mouse-pressed? true)
     (.clickAndHold driver))
  ([driver elem]
     (reset! mouse-pressed? true)
     (.clickAndHold driver elem)))

(defn release
  "Release the mouse in the center of the given web element. If no element is given, release the mouse button at the current location."
  ([driver]
     (when @mouse-pressed?
       (.release driver))
     (reset! mouse-pressed? false)
     driver) ; return driver to support continuation
  ([driver elem]
     (when @mouse-pressed?
       (.release driver elem))
     (reset! mouse-pressed? false)
     driver)) ; ditto

(defn context-click
  "Do a context click (right-click or control-click) on the given web element"
  [driver elem]
  (.contextClick driver elem))

(defmacro with-key*
  [driver key & actions]
  `((fn [~'driver]
      (.keyDown ~'driver ~key)
      (run* ~'driver ~@actions)
      (.keyUp ~'driver ~key)) ~driver))

(defmacro with-shift
  "Execute the actions with the shift key down."
  [driver & actions]
  `(with-key* ~driver Keys/SHIFT ~@actions))

(defmacro with-control
  "Execute the actions with the control key down."
  [driver & actions]
  `(with-key* ~driver Keys/CONTROL ~@actions))

(defmacro with-alt
  "Execute the actions with the alt key down."
  [driver & actions]
  `(with-key* ~driver Keys/ALT ~@actions))

(defn type
  "Type the given text. Does not focus on the given field (if any), however TAB will work to move focus from field to field."
  ([driver txt]
     (.sendKeys driver (into-array [txt])))
  ([driver elem txt]
     (.sendKeys driver elem (into-array [txt]))))

(defn move-mouse
  "Move the current mouse position by the given offsets."
  [driver x-offset y-offset]
  (.moveByOffset driver x-offset y-offset))

(defn move-mouse-to
  "Move the mouse to the center of the given element"
  [driver elem]
  (.moveToElement driver elem))

(defn move-mouse-relative-to
  "Move the mouse to the given offsets relative to the top left corner of the given element"
  [driver elem x y]
  (.moveToElement driver elem x y))

(defn drag-and-drop
  "Drag the source element on top of the destination element and drop it. Alternately, drag the source element by a given x and y offset, and drop it there."
  ([driver src dest]
     (.dragAndDrop driver src dest))
  ([driver src x y]
     (.dragAndDropBy driver src x y)))

(defn pause
  "Call Thread/sleep for the given number of milliseconds, uses a dummy first argument to be compatible with salty.actions/run"
  [dummy millis]
  (Thread/sleep millis))

