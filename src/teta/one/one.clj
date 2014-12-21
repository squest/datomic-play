(ns teta.one.one)

(defn expt [a m]
	(reduce *' (repeat m a)))

(def target (expt 10 999))

(defn fibo [lim]
	(loop [a 1 b 0 i (int 1)]
		(if (> a lim)
			i
			(recur (+' a b) a (inc i)))))
