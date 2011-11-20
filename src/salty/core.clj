(ns salty.core
  (:require [salty.impl :as impl]
            [salty.driver :as driver]
            [salty.find :as find]))

(defn browser
  "Create a new instance of a given browser driver. Supported
drivers are :firefox, :htmlunit and :ie. The default browser
is :firefox."
  [& params]
  (if-let [browser (first params)]
    (driver/init browser (next params))
    (driver/init :firefox params)))

