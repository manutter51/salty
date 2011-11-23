(ns salty.util)

(defn get-options
  "Given a list of arguments like [:a 1 :b 2 3 4] and
a set of keys like #{:a :b}, return a map with {:a 1 :b 2 :args [3 4]}.
If any option key is not found in the array, return nil for that value."
  [args opt-set]
  (let [opt-set (into #{} (map keyword opt-set))
        initial-map (into {:args []}
                          (for [o opt-set] 
                            [o nil]))]
    (loop [m initial-map args args]
      (if (nil? args)
        m
        (let [a (first args)
              b (second args)
              r (nnext args)]
          (if (opt-set a)
            (recur (assoc m a b) r)
            (recur (assoc m :args
                          (conj (m :args) a))
                   (next args))))))))
