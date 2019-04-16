/*
 * Copyright (C) 2015-2018 UTN-FRRe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ar.edu.utn.frre.dacs.loan.scoring;

public enum ScoringRate {
	EXCEPTIONAL,  // 800 - 850
	VERY_GOOD,    // 740 - 799
	GOOD,         // 670 - 739
	FAIR,         // 580 - 669 
	VERY_POOR     // 300 - 579
;

	public static ScoringRate fromRate(Integer rate) {
		if(rate == null)
			throw new IllegalArgumentException("Invalid rate: null");
		if(rate.intValue() < 300 || rate.intValue() > 850)
			throw new IllegalArgumentException("Rate must be in the range of [300-850]");
		if(rate.intValue() < 580)
			return VERY_POOR;
		if(rate.intValue() < 670)
			return FAIR;
		if(rate.intValue() < 740)
			return GOOD;
		if(rate.intValue() < 800)
			return VERY_GOOD;
		return EXCEPTIONAL;
	}
}
