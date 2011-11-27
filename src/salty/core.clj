(ns salty.core
  (:import org.openqa.selenium.WebDriver)
  (:require [salty.impl :as impl]
            [salty.driver :as driver]
            [salty.find :as find]
            [salty.util :as util]
            [salty.ui :as ui]
            [salty.cmd :as cmd]))

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
or else fail fast."
  [& params]
  (let [b (first params)]
    (if-not (instance? WebDriver b)
      (throw (Exception. "Browser not valid, use this fn only inside salty.core/with-browser"))
      b)))

(defmacro with-browser
  "Execute the statements using the supplied browser,
optionally starting at the given url. 

Example:
  (with-browser :firefox
                :at \"http://www.cnn.com\"
    (-> (salty.find/find-by-id \"hdr-search-box\") 
        (salty.ui/type-in \"clojure\\n\" "
  [browser & more]
  (let [parsed-args (util/get-options more #{:at})
        body (:args parsed-args)]
    `(binding [impl/*driver* (if (keyword? ~browser)
                                (get-browser ~browser)
                                ~browser)]
       (if-let [start# ~(:at parsed-args)]
         (impl/get-url impl/*driver* start#))
       ~@body)))

(defn go-to-url
  [url]
  (impl/go-to-url (ensure-browser impl/*driver*) url))

(defn end
  []
  (impl/end (ensure-browser impl/*driver*)))

(defn add-cookie
  "Set a cookie on the same domain as the last page loaded"
  [k v]
  (impl/add-cookie (ensure-browser impl/*driver*) k v))

(defn get-cookies
  "Get all current cookies for the current domain"
  []
  (impl/get-cookies (ensure-browser impl/*driver*)))

(defn delete-cookie
  "Delete the cookie with the given name."
  [cookie]
  (impl/delete-cookie (ensure-browser impl/*driver*)))

(defmacro run
  [& actions]
  `(cmd/run (ensure-browser impl/*driver*) ~@actions))
