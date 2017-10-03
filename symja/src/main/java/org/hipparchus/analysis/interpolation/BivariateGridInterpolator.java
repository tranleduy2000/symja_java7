/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hipparchus.analysis.interpolation;

import org.hipparchus.analysis.BivariateFunction;
import org.hipparchus.exception.MathIllegalArgumentException;

/**
 * Interface representing a bivariate real interpolating function where the
 * sample points must be specified on a regular grid.
 */
public interface BivariateGridInterpolator {
    /**
     * Compute an interpolating function for the dataset.
     *
     * @param xval All the x-coordinates of the interpolation points, sorted
     *             in increasing order.
     * @param yval All the y-coordinates of the interpolation points, sorted
     *             in increasing order.
     * @param fval The values of the interpolation points on all the grid knots:
     *             {@code fval[i][j] = f(xval[i], yval[j])}.
     * @return a function which interpolates the dataset.
     * @throws MathIllegalArgumentException if any of the arrays has zero length.
     * @throws MathIllegalArgumentException if the array lengths are inconsistent.
     * @throws MathIllegalArgumentException if the array is not sorted.
     * @throws MathIllegalArgumentException if the number of points is too small for
     *                                      the order of the interpolation
     */
    BivariateFunction interpolate(double[] xval, double[] yval,
                                  double[][] fval)
            throws MathIllegalArgumentException;
}
