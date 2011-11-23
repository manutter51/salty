(ns salty.find
  (:import [org.openqa.selenium WebDriver WebElement By])
  (:use [clojure.string :only [lower-case]]))

(defn- camel-to-dash
  [s]
  (apply str (map #(if (Character/isUpperCase %)
                     (str "-" (lower-case %))
                     %)
                  s)))

(defn- find*
  [driver-or-elem by]
  (.findElement driver-or-elem by))

(defn- find-all*
  [driver-or-elem by]
  (.findElements driver-or-elem by))

(defmacro make-find
  [strategy docs]
  (let [f-name# (symbol (str "find-by-" (camel-to-dash (str strategy))))
        docs# (str "Find a page element " docs)
        by-class# (symbol (str "By/" strategy))]
    `(defn ~f-name#
       ~docs#
       [~'driver-or-elem ~strategy ~'all?]
       (if ~'all?
         (find-all* (~by-class# ~strategy))
         (find* (~by-class# ~strategy))))))

;; Generates functions like the following:

;(defn find-by-id
;  "Find a page element by its HTML ID attribute."
;  [driver-or-elem id all?]
;(if all?
;  (find-all* By/id id)
; (find* (By/id id))))

;(defn find-by-name
;  "Find a page element by its name attribute"
;  [driver-or-elem name all?]
;(if all?
;  (find-all* (By/name name))
;  (find* (By/name name))))

(make-find id "by the HTML ID attribute.")
(make-find name "by the HTML name attribute")
(make-find className "by the CSS class name attribute")
(make-find cssSelector "by CSS selector (i.e. jQuery-style)")
(make-find linkText "by the text displayed in a link (exact match)")
(make-find partialLinkText "by part of the text displayed in a link")
(make-find tagName "by HTML tag name (e.g. \"body\")")
(make-find xpath "by XPATH")

;; Odd man out
(defn find-by-javascript
  "Find a page element by executing arbitrary JavaScript
code. The JavaScript code MUST return a single DOM element
(and specifically NOT a jQuery object--use get(0) or
something similar)."
  [driver script]
  (.executeScript driver script))
