(ns salty.core
  (:require [salty.impl :as impl]
            [salty.driver :as driver]
            [salty.find :as find]
            [salty.util :as util]))

(defn get-browser
  "Create a new instance of a given browser driver. Supported
drivers are :firefox, :htmlunit and :ie. The default browser
is :firefox."
  [& params]
  (if-let [browser (first params)]
    (driver/init browser (next params))
    (driver/init :firefox params)))

(defn- ensure-browser
  "Makes sure its argument is a valid WebDriver instance,
creating a new WebDriver if the argument is just a keyword."
  [& params]
  (let [b (first params)]
    (if (keyword? b)
      (apply get-browser params)
      b)))

(defmacro with
  "Execute the statements using the supplied browser,
optionally starting at the given url. 

Example:
  (with :firefox
        :at \"http://www.cnn.com\"
    (-> (find-by-id \"hdr-search-box\") 
        (type \"clojure\\n\" "
  [browser & more]
  (let [browser (if (keyword? browser)
                  (get-browser browser)
                  browser)
        parsed-args (util/get-options more #{:at :params})
        body (:args parsed-args)]
    `(binding [impl/*browser* browser]
      (if-let [start# ~(:at parsed-args)]
        (impl/get impl/*browser* start#))
      ~@body)))
