(ns salty.driver
  (:import [org.openqa.selenium WebDriver]
           org.openqa.selenium.firefox.FirefoxDriver
           org.openqa.selenium.htmlunit.HtmlUnitDriver
           org.openqa.selenium.ie.InternetExplorerDriver))

(defmulti init (fn [x & more] x))

(defmethod init :firefox [& params]
  (let [[browser profile & more] params]
    (if profile
      (FirefoxDriver. profile)
      (FirefoxDriver.))))

(defmethod init :default [& params]
  (when (seq params)
    (throw (Exception.
            (str "Unknown browser type, please use :firefox, :ie, :htmlunit or :htmlunit+js instead of "
                 (first params)))))
  (FirefoxDriver. params))

(defmethod init :htmlunit [& params]
  (HtmlUnitDriver.))

(defmethod init :htmlunit+js [& params]
  (HtmlUnitDriver. true))

(defmethod init :ie [& params]
  (InternetExplorerDriver.))