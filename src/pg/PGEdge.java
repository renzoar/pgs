/* 
 * Copyright 2020 Renzo Angles (http://renzoangles.com/)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pg;

public class PGEdge extends PGObject{
    
    private final PGNode source_node;
    private final PGNode target_node;

    public PGEdge(String _label, PGNode source, PGNode target){
        this.addLabel(_label);
        this.source_node = source;
        this.target_node = target;
    }
    
    public PGEdge(PGNode source, PGNode target){
        this.source_node = source;
        this.target_node = target;
    }
    
    public PGNode getSourceNode(){
        return this.source_node;
    }
    
    public PGNode getTargetNode(){
        return this.target_node;
    }
    
}