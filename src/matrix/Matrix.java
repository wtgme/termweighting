package matrix;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import util.Util;

/*
 * 
 * 			T			!T
 * 	c		a(TP)		b(FP)
 * 	!c		c(FN)		d(TN)
 */
public class Matrix {
	// for TF calculation
	public Map<Integer, Map<Integer, Integer>> matrix_tf_c_t; // class_token_value
	public Map<Integer, Map<Integer, Integer>> matrix_tf_t_c; // token_class_value
	// for DF calculation
	public Map<Integer, Map<Integer, Integer>> matrix_df_c_d; // class_document_value
	public Map<Integer, Map<Integer, Integer>> matrix_df_d_c; // document_class_value
	// for TF calculation
	public Map<Integer, Integer> class_tf_size;
	public Map<Integer, Integer> token_tf_size;
	// for DF calculation
	public Map<Integer, Integer> class_df_size;
	public Map<Integer, Integer> token_df_size;
	public int N;// document number in a collection
	public int C;// category number in a collection
	public double V;// frequency sum of all tokens in a collection

	public Matrix() {
		matrix_tf_c_t = new HashMap<Integer, Map<Integer, Integer>>();
		matrix_tf_t_c = new HashMap<Integer, Map<Integer, Integer>>();
		matrix_df_c_d = new HashMap<Integer, Map<Integer, Integer>>();
		matrix_df_d_c = new HashMap<Integer, Map<Integer, Integer>>();
		// the number of document in each category
		class_tf_size = new HashMap<Integer, Integer>();
		token_tf_size = new HashMap<Integer, Integer>();
		class_df_size = new HashMap<Integer, Integer>();
		token_df_size = new HashMap<Integer, Integer>();
		N = 0;
		C = 0;
		V = 0;
	}

	public Set<Integer> getTokenSet() {
		return matrix_tf_t_c.keySet();
	}

	public Integer getTokenSize() {
		return matrix_tf_t_c.size();
	}

	/**
	 * @param tokens
	 *            输入文档
	 * @param label
	 *            文档的类标记
	 */
	// public void setValue(String[] tokens, Integer label) {
	// Map<Integer, Integer> tokens_valuesMap = matrix1.get(label);
	// Integer classSizeValue = classSize.get(label);
	// if (tokens_valuesMap == null) {
	// tokens_valuesMap = new HashMap<Integer, Integer>();
	// classSizeValue = 0;
	// C++;
	// }
	// classSize.put(label, classSizeValue + 1);
	//
	// for (String tokenV : tokens) {
	// String[] t_v = tokenV.split(":");
	// Integer token = Integer.valueOf(t_v[0]);
	// V += Integer.valueOf(t_v[1]);
	// Integer tokenSizeValue = tokenSize.get(token);
	// if (tokenSizeValue == null) {
	// tokenSizeValue = 0;
	// }
	// tokenSize.put(token, tokenSizeValue + 1);
	//
	// Integer token_value = tokens_valuesMap.get(token);
	// if (token_value == null) {
	// token_value = 0;
	// }
	// tokens_valuesMap.put(token, token_value + 1);
	//
	// // set in Matrix2 token_class_value
	// Map<Integer, Integer> classes_valueMap = matrix2.get(token);
	// if (classes_valueMap == null) {
	// classes_valueMap = new HashMap<Integer, Integer>();
	// }
	// Integer class_value = classes_valueMap.get(label);
	// if (class_value == null) {
	// class_value = 0;
	// }
	// classes_valueMap.put(label, class_value + 1);
	// matrix2.put(token, classes_valueMap);
	// }
	// // set in Matrix1 class_token_value
	// matrix1.put(label, tokens_valuesMap);
	// N++;
	// }

	/*
	 * for CU vale
	 */
	// public void setValue(String[] tokens, Integer label) {
	// Map<Integer, Integer> tokens_valuesMap = matrix1.get(label);
	// Integer classSizeValue = classSize.get(label);
	// if (tokens_valuesMap == null) {
	// tokens_valuesMap = new HashMap<Integer, Integer>();
	// classSizeValue = 0;
	// C++;
	// }
	//
	// for (String tokenV : tokens) {
	// String[] t_v = tokenV.split(":");
	// Integer token = Integer.valueOf(t_v[0]);
	// {
	// V += 1;
	// classSizeValue += 1;
	// Integer tokenSizeValue = tokenSize.get(token);
	// if (tokenSizeValue == null) {
	// tokenSizeValue = 0;
	// }
	// tokenSize.put(token, tokenSizeValue + 1);
	//
	// Integer token_value = tokens_valuesMap.get(token);
	// if (token_value == null) {
	// token_value = 0;
	// }
	// tokens_valuesMap.put(token, token_value + 1);
	//
	// // set in Matrix2 token_class_value
	// Map<Integer, Integer> classes_valueMap = matrix2.get(token);
	// if (classes_valueMap == null) {
	// classes_valueMap = new HashMap<Integer, Integer>();
	// }
	// Integer class_value = classes_valueMap.get(label);
	// if (class_value == null) {
	// class_value = 0;
	// }
	// classes_valueMap.put(label, class_value + 1);
	// matrix2.put(token, classes_valueMap);
	// }
	// }
	//
	// classSize.put(label, classSizeValue);
	// // set in Matrix1 class_token_value
	// matrix1.put(label, tokens_valuesMap);
	// N++;
	// }

	/*
	 * for Entropy
	 */

	public void setValue(String[] tokens, Integer label) {
		Map<Integer, Integer> tokens_valuesMap = matrix_tf_c_t.get(label);
		Integer classSizeValue = class_tf_size.get(label);
		if (tokens_valuesMap == null) {
			tokens_valuesMap = new HashMap<Integer, Integer>();
			classSizeValue = 0;
			C++;
		}

		Map<Integer, Integer> docs_valuesMap = matrix_df_c_d.get(label);
		Integer classDfSizeValue = class_df_size.get(label);
		if (docs_valuesMap == null) {
			docs_valuesMap = new HashMap<Integer, Integer>();
			classDfSizeValue = 0;
		}

		class_df_size.put(label, classDfSizeValue + 1);
		// System.out.println(class_df_size.size());
		for (String tokenV : tokens) {
			String[] t_v = tokenV.split(":");
			Integer token = Integer.valueOf(t_v[0]);
			{
				Integer frequency = Integer.valueOf(t_v[1]);
				V += frequency;

				classSizeValue += frequency;

				// add the token frequency for each token
				Integer tokenSizeValue = token_tf_size.get(token);
				if (tokenSizeValue == null) {
					tokenSizeValue = 0;
				}
				token_tf_size.put(token, tokenSizeValue + frequency);

				// add the document number for each token
				Integer tokenDfValue = token_df_size.get(token);
				if (tokenDfValue == null) {
					tokenDfValue = 0;
				}
				token_df_size.put(token, tokenDfValue + 1);

				// add token frequency to each token in a category
				Integer token_value = tokens_valuesMap.get(token);
				if (token_value == null) {
					token_value = 0;
				}
				tokens_valuesMap.put(token, token_value + frequency);

				// add document frequency to each token in a category
				token_value = docs_valuesMap.get(token);
				if (token_value == null) {
					token_value = 0;
				}
				docs_valuesMap.put(token, token_value + 1);

				// set in Matrixtf_t_c
				Map<Integer, Integer> classes_valueMap = matrix_tf_t_c
						.get(token);
				if (classes_valueMap == null) {
					classes_valueMap = new HashMap<Integer, Integer>();
				}
				Integer class_value = classes_valueMap.get(label);
				if (class_value == null) {
					class_value = 0;
				}
				classes_valueMap.put(label, class_value + frequency);
				// System.out.println(label + "\t" + class_value);
				matrix_tf_t_c.put(token, classes_valueMap);

				// set MatrixDf_d_c
				classes_valueMap = matrix_df_d_c.get(token);
				if (classes_valueMap == null) {
					classes_valueMap = new HashMap<Integer, Integer>();
				}
				class_value = classes_valueMap.get(label);
				if (class_value == null) {
					class_value = 0;
				}
				classes_valueMap.put(label, class_value + 1);
				matrix_df_d_c.put(token, classes_valueMap);
			}
		}
		class_tf_size.put(label, classSizeValue);

		// set in Matrix1 class_token_value
		matrix_tf_c_t.put(label, tokens_valuesMap);
		matrix_df_c_d.put(label, docs_valuesMap);
		N++;
	}

	/**
	 * @param token
	 * @param label
	 * @return the number of documents in positive category that contain the
	 *         term
	 */
	public int getA(Integer token, Integer label) {
		Map<Integer, Integer> token_valueMap = matrix_df_c_d.get(label);
		if (token_valueMap == null) {
			return 0;
		} else {
			Integer value = token_valueMap.get(token);
			if (value == null) {
				return 0;
			} else {
				return value;
			}
		}
	}

	public int getTFA(Integer token, Integer label) {
		Map<Integer, Integer> token_valueMap = this.matrix_tf_c_t.get(label);
		if (token_valueMap == null) {
			return 0;
		} else {
			Integer value = token_valueMap.get(token);
			if (value == null) {
				return 0;
			} else {
				return value;
			}
		}
	}

	public int getClassTFSize(Integer label) {
		return this.class_tf_size.get(label);
	}

	public int getClassTFNCSize(Integer label) {
		Set<Integer> keys = this.class_tf_size.keySet();
		int sum = 0;
		for (Integer key : keys) {
			if (key != label)
				sum += this.class_tf_size.get(key);
		}
		return sum;
	}

	/*
	 * get Max Frequency of a term in classes
	 */
	public int getTFMAX(Integer token) {
		Map<Integer, Integer> class_vlaueMap = this.matrix_tf_t_c.get(token);
		if (class_vlaueMap == null) {
			return 0;
		} else {
			int max = 0;
			Set<Integer> keys = class_vlaueMap.keySet();
			for (Integer key : keys) {
				int value = class_vlaueMap.get(key);
				if (max <= value) {
					max = value;
				}
			}
			return max;
		}
	}

	/*
	 * get Sum Frequency of a term in classes
	 */
	public int getTFSum(Integer token) {
		Map<Integer, Integer> class_vlaueMap = this.matrix_tf_t_c.get(token);
		if (class_vlaueMap == null) {
			return 0;
		} else {
			int sum = 0;
			Set<Integer> keys = class_vlaueMap.keySet();
			for (Integer key : keys) {
				int value = class_vlaueMap.get(key);
				sum += value;
			}
			return sum;
		}
	}

	/**
	 * @param token
	 * @param label
	 * @return the number of documents in the positive category that do not
	 *         contain the term
	 */
	public int getB(int a, Integer label) {
		Integer classSizeValue = class_df_size.get(label);
		if (classSizeValue == null) {
			return 0;
		} else {
			return classSizeValue - a;
		}
	}

	/**
	 * @param token
	 * @param label
	 * @return the number of documents in the negative category that contain the
	 *         term
	 */
	public int getC(int a, Integer token) {
		Integer tokenSizeValue = token_df_size.get(token);
		if (tokenSizeValue == null) {
			return 0;
		} else {
			return tokenSizeValue - a;
		}
	}

	/**
	 * @param a
	 * @param b
	 * @param c
	 * @return the number of documents in the negative category that do not
	 *         contain the term
	 */
	public int getD(int a, int b, int c) {
		return (N - a - b - c);
	}

	public int getN() {
		return this.N;
	}

	public int getCat() {
		return this.C;
	}

	public double getV() {
		return this.V;
	}

	/**
	 * @param token
	 * @return the category frequency of term
	 */
	public int getCF(Integer token) {
		Map<Integer, Integer> class_valueMap = this.matrix_df_d_c.get(token);
		return class_valueMap.size();
	}

	/**
	 * @param token
	 * @return entropy value of the term
	 */
	public Double getEntropy(Integer token) {
		Map<Integer, Integer> class_valueMap = this.matrix_tf_t_c.get(token);
		if (class_valueMap == null) {
			return null;
		} else {
			double sum = 0.0;
			Set<Integer> keySet = class_valueMap.keySet();
			for (Iterator<Integer> iterator = keySet.iterator(); iterator
					.hasNext();) {
				Integer key = iterator.next();
				sum += class_valueMap.get(key);
			}
			double entropy = 0.0;
			for (Iterator<Integer> iterator = keySet.iterator(); iterator
					.hasNext();) {
				Integer key = iterator.next();
				double pro = (double) class_valueMap.get(key) / sum;
				entropy += pro * Util.log2(pro);
				// entropy += pro * Math.log(pro);
				// entropy += pro * Util.log33(pro);
			}
			return -entropy;
		}
	}

	/**
	 * @param token
	 * @return the balance entropy of the term
	 */
	public Double getBalanceEntropy(Integer token) {
		Map<Integer, Integer> class_valueMap = this.matrix_tf_t_c.get(token);
		if (class_valueMap == null) {
			System.out.println("Entropy Calculation Error!");
			return null;
		} else {
			double sum = 0.0;
			double[] values = new double[C];
			for (int i = 0; i < C; i++) {
				Integer value = class_valueMap.get(i + 1);
				if (value == null)
					value = 0;
				values[i] = (double) value / this.class_tf_size.get(i + 1);
				sum += values[i];
			}
			double entropy = 0.0;
			for (int i = 0; i < C; i++) {
				double pro = (double) values[i] / sum;
				entropy += pro * Util.log2(pro);
			}
			return -entropy;

			// double sum = 0.0;
			// Set<Integer> keySet = class_valueMap.keySet();
			// for (Iterator<Integer> iterator = keySet.iterator(); iterator
			// .hasNext();) {
			// Integer key = iterator.next();
			// sum += (double) class_valueMap.get(key)
			// / this.class_tf_size.get(key);
			// }
			// double entropy = 0.0;
			// for (Iterator<Integer> iterator = keySet.iterator(); iterator
			// .hasNext();) {
			// Integer key = iterator.next();
			// double pro = ((double) class_valueMap.get(key) /
			// this.class_tf_size
			// .get(key)) / sum;
			// entropy += pro * Util.log2(pro);
			// // entropy += pro * Math.log(pro);
			// // entropy += pro * Util.log33(pro);
			// }
			// return -entropy;
		}
	}

	/**
	 * @param token
	 * @return the Laplace smooth entropy of term in case of noise
	 */
	public Double getLapSmoothEntropy(Integer token) {
		Map<Integer, Integer> class_valueMap = this.matrix_tf_t_c.get(token);
		if (class_valueMap == null) {
			return null;
		} else {
			double sum = 0.0;
			double[] values = new double[C];
			for (int i = 0; i < C; i++) {
				Integer value = class_valueMap.get(i + 1);
				if (value == null)
					value = 0;
				values[i] = value + 1;
				sum += values[i];
			}
			double entropy = 0.0;
			for (int i = 0; i < C; i++) {
				double pro = (double) values[i] / sum;
				entropy += pro * Util.log2(pro);
			}
			return -entropy;
		}
	}

	/**
	 * @param token
	 * @return the absolute discounting smooth entropy of term in case of noise
	 */
	public Double getADSmoothEntropy(Integer token) {
		Map<Integer, Integer> class_valueMap = this.matrix_tf_t_c.get(token);
		if (class_valueMap == null) {
			return null;
		} else {
			double sig = 0.6;
			double[] values = new double[C];
			for (int i = 0; i < C; i++) {
				int classLabel = i + 1;
				Integer value = class_valueMap.get(classLabel);
				if (value == null)
					value = 0;
				double max = value - sig;
				max = Math.max(max, 0.0);

				double classsize = (double) this.matrix_tf_t_c.get(token)
						.size();
				double llh = (double) this.class_tf_size.get(classLabel)
						/ this.V;
				llh = sig * classsize * llh;

				double tfToken = (double) this.token_tf_size.get(token);

				values[i] = (max + llh) / (tfToken);
			}
			double entropy = 0.0;
			for (int i = 0; i < C; i++) {
				double pro = (double) values[i];
				entropy += pro * Util.log2(pro);
			}
			return -entropy;
		}
	}

	/**
	 * @param token
	 * @return the Jelinek-Mercer smooth entropy of term in case of noise
	 */
	public Double getJMSmoothEntropy(Integer token) {
		Map<Integer, Integer> class_valueMap = this.matrix_tf_t_c.get(token);
		if (class_valueMap == null) {
			return null;
		} else {
			double lamd = 0.5;
			double[] values = new double[C];
			for (int i = 0; i < C; i++) {
				int classLabel = i + 1;
				Integer value = class_valueMap.get(classLabel);
				if (value == null)
					value = 0;

				double llh = (double) this.class_tf_size.get(classLabel)
						/ this.V;

				// double classsize = (double) this.matrix_tf_t_c.get(token)
				// .size();
				double classsize = (double) this.token_tf_size.get(token);

				values[i] = (1 - lamd) * (value / classsize) + lamd * llh;

			}
			double entropy = 0.0;
			for (int i = 0; i < C; i++) {
				double pro = (double) values[i];
				entropy += pro * Util.log2(pro);
			}
			return -entropy;
		}
	}

	/**
	 * @param token
	 * @return the Dirichlet smooth entropy of term in case of noise
	 */
	public Double getDirSmoothEntropy(Integer token) {
		Map<Integer, Integer> class_valueMap = this.matrix_tf_t_c.get(token);
		if (class_valueMap == null) {
			return null;
		} else {
			double mu = 0.95;
			double[] values = new double[C];
			for (int i = 0; i < C; i++) {
				int classLabel = i + 1;
				Integer value = class_valueMap.get(classLabel);
				if (value == null)
					value = 0;

				double llh = (double) this.class_tf_size.get(classLabel)
						/ this.V;

				double classsize = (double) this.token_tf_size.get(token);

				values[i] = (value + mu * llh) / (classsize + mu);
			}
			double entropy = 0.0;
			for (int i = 0; i < C; i++) {
				double pro = (double) values[i];
				entropy += pro * Util.log2(pro);
			}
			return -entropy;
		}
	}

	/**
	 * @param token
	 * @return the Two-stage smooth entropy of term in case of noise
	 */
	public Double getTSSmoothEntropy(Integer token) {
		Map<Integer, Integer> class_valueMap = this.matrix_tf_t_c.get(token);
		if (class_valueMap == null) {
			return null;
		} else {
			double mu = 100;
			double lamd = 0.6;
			double[] values = new double[C];
			for (int i = 0; i < C; i++) {
				int classLabel = i + 1;
				Integer value = class_valueMap.get(classLabel);
				if (value == null)
					value = 0;

				double llh = (double) this.class_tf_size.get(classLabel)
						/ this.V;

				double classsize = (double) this.token_tf_size.get(token);

				values[i] = (1 - lamd)
						* ((value + mu * llh) / (classsize + mu)) + lamd
						* (llh);
			}
			double entropy = 0.0;
			for (int i = 0; i < C; i++) {
				double pro = (double) values[i];
				entropy += pro * Util.log2(pro);
			}
			return -entropy;
		}
	}

	/**
	 * @param token
	 * @return the smooth balance entropy of the term
	 */
	public Double getSmoothBalanceEntropy(Integer token) {
		Map<Integer, Integer> class_valueMap = this.matrix_tf_t_c.get(token);
		if (class_valueMap == null) {
			System.out.println("Entropy Calculation Error!");
			return null;
		} else {
			double sum = 0.0;
			double[] values = new double[C];
			for (int i = 0; i < C; i++) {
				int classLabel = i + 1;
				Integer value = class_valueMap.get(classLabel);
				if (value == null)
					value = 0;
				double v = (double) (value + 1)
						/ (this.class_tf_size.get(classLabel) + this.token_tf_size
								.size());
				values[i] = v;
				sum += v;
			}
			double entropy = 0.0;
			for (int i = 0; i < C; i++) {
				double pro = values[i] / sum;
				entropy += pro * Util.log2(pro);
			}
			return -entropy;
		}
	}

	/*
	 * for ECCD the left part
	 */
	public Double getDfProb(Integer token, Integer c) {
		Integer class_size = this.class_df_size.get(c);
		if (class_size == null) {
			System.out.println("class non:" + c);
		}

		Integer token_size = this.token_df_size.get(token);
		if (token_size == null) {
			System.out.println("token non:" + token);
		}
		Map<Integer, Integer> token_value = this.matrix_df_c_d.get(c);
		Integer value = 0;
		if (token_value != null) {
			value = token_value.get(token);
			if (value == null) {
				value = 0;
			}
		}
		double pro = ((double) value / class_size)
				- ((double) (token_size - value) / (this.N - class_size));
		return pro;
	}

	/*
	 * get Max DF of a token in a class
	 */
	public Integer getMaxDF(Integer token) {
		Map<Integer, Integer> class_df = this.matrix_df_d_c.get(token);
		Collection<Integer> valueSet = class_df.values();
		Integer max = 0;
		for (Integer value : valueSet) {
			if (value >= max) {
				max = value;
			}
		}
		return max;
	}

	/*
	 * get Max TF of a token in a class
	 */
	public Integer getMaxTF(Integer token) {
		Map<Integer, Integer> class_df = this.matrix_tf_t_c.get(token);
		Collection<Integer> valueSet = class_df.values();
		Integer max = 0;
		for (Integer value : valueSet) {
			if (value >= max) {
				max = value;
			}
		}
		return max;
	}

	/*
	 * get Max TF of a token in a class
	 */
	public Double getAvgDF(Integer token) {
		Map<Integer, Integer> class_df = this.matrix_df_d_c.get(token);
		Set<Integer> keys = class_df.keySet();
		double avg = 0.0;
		int cates = 0;
		for (Integer key : keys) {
			avg += class_df.get(key);
			cates++;
		}
		avg /= cates;
		return avg;
	}

	public Integer getDF(Integer tokne) {
		Integer df = this.token_df_size.get(tokne);
		return df;
	}
}
