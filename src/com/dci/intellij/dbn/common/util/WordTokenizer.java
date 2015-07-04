/*
 * Copyright 2012-2014 Dan Cioca
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dci.intellij.dbn.common.util;

import java.util.List;
import java.util.ArrayList;

public class WordTokenizer {
    List<String> tokens = new ArrayList<String>();

    public WordTokenizer(String string) {
        //String[] allTokens = string.split("\\b\\s+|\\s+\\b|\\b");
        String[] tokens1 = string.split("\\b");
        for (String token1 : tokens1) {
            token1 = token1.trim(); 
            if (token1.length() > 0) {
                if (isSplittableToken(token1)) {
                    String[] tokens2 = token1.split("\\B");
                    for (String token2 : tokens2) {
                        token2 = token2.trim();
                        if (token2.length() > 0) {
                            tokens.add(token2);
                        }
                    }
                } else {
                    tokens.add(token1);
                }
            }
        }

    }
    
    private boolean isSplittableToken(String token) {
        if (token.length() > 1) {
            char chr = token.charAt(0);
            return !Character.isLetter(chr) && !Character.isDigit(chr) && chr != '_';            
        }
        return false;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public static void main(String[] args) {
        WordTokenizer wordTokenizer = new WordTokenizer("return decimal(5,2), p_customer_id INT, p_effective_date DATETIME");

        
    }
}
