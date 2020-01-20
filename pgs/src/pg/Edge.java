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

public class Edge extends Object{
    
    private final Node source_node;
    private final Node target_node;

    public Edge(String _label, Node source, Node target){
        this.addLabel(_label);
        this.source_node = source;
        this.target_node = target;
    }
    
    public Edge(Node source, Node target){
        this.source_node = source;
        this.target_node = target;
    }
    
    public Node getSourceNode(){
        return this.source_node;
    }
    
    public Node getTargetNode(){
        return this.target_node;
    }
    
}