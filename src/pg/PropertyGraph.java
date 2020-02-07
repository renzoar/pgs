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

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;

public class PropertyGraph {
    
    ArrayList<PGNode> nodes = new ArrayList();
    ArrayList<PGEdge> edges = new ArrayList();    
    ArrayList<PGProperty> properties = new ArrayList(); 
    int free_id = 0;
    
    public PropertyGraph(){
        
    }
    
    public Iterator<PGNode> getNodes(){
        return nodes.iterator();
    }
    
    public Iterator<PGEdge> getEdges(){
        return edges.iterator();
    }

    public Iterator<PGProperty> getProperties(){
        return properties.iterator();
    }    
    
    public PGNode createNode(){
        PGNode node = new PGNode();
        node.setId(free_id++);
        nodes.add(node);
        return node;
    }
    
    public PGNode createNode(String label){
        PGNode node = new PGNode();
        node.setId(free_id++);
        node.addLabel(label);
        nodes.add(node);
        return node;
    }  
    
    public PGEdge createEdge(PGNode source, PGNode target){
        PGEdge edge = new PGEdge(source,target);
        edge.setId(free_id++);
        edges.add(edge);
        return edge;
    }
    
    public PGEdge createEdge(String label, PGNode source, PGNode target){
        PGEdge edge = new PGEdge(label, source,target);
        edge.setId(free_id++);
        this.edges.add(edge);
        return edge;
    }
    
    public PGProperty createProperty(PGNode owner, String name, String value){
        PGProperty prop = new PGProperty(owner,name,value);
        prop.setId(free_id++);
        this.properties.add(prop);
        if(!nodes.contains(owner)){
            nodes.add(owner);
        }
        prop.setOwner(owner);
        owner.addProperty(prop);
        return prop;
    }
    
    public PGProperty createProperty(PGEdge owner, String name, String value){
        PGProperty prop = new PGProperty(owner,name,value);
        prop.setId(free_id++);
        this.properties.add(prop);
        if(!edges.contains(owner)){
            edges.add(owner);
        }
        prop.setOwner(owner);
        owner.addProperty(prop);
        return prop;
    }
    
    public PGNode getNodeById(int id){
        Iterator<PGNode> it = this.nodes.iterator();
        while(it.hasNext()){
            PGNode node = it.next();
            if(node.id == id){
                return node;
            }
        }
        return null;
    }
    
    public PGEdge getEdgeById(int id){
        Iterator<PGEdge> it = this.edges.iterator();
        while(it.hasNext()){
            PGEdge edge = it.next();
            if(edge.id == id){
                return edge;
            }
        }
        return null;
    }   
    
    public PGProperty getPropertyById(int id){
        Iterator<PGProperty> it = this.properties.iterator();
        while(it.hasNext()){
            PGProperty prop = it.next();
            if(prop.getId() == id){
                return prop;
            }
        }
        return null;
    }    
    
    public void exportAsYPG(String outputFileName){
        String id;
        String labels = "";
        String properties = "";
        String source_id;
        String target_id;
        PGNode node;
        PGEdge edge;
        PGProperty prop;
        Iterator<String> itl;
        Iterator<PGProperty> itp;
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "UTF-8"));
            //process nodes
            Iterator<PGNode> itn = this.nodes.iterator();
            while(itn.hasNext()){
                node = itn.next();
                id = "(" + node.getId() + ")";
                //process labels
                labels = "[";
                itl = node.getLabels();
                while(itl.hasNext()){
                    labels = labels + itl.next();
                }
                labels = labels + "]";
                labels = labels.replace(",]","]");
                //process (node) properties 
                properties = "{";
                itp = node.getProperties();
                while(itp.hasNext()){
                    prop = itp.next();
                    properties = properties + prop.getLabel() + ":\"" + prop.getValue() + "\",";
                }
                properties = properties + "}";
                properties = properties.replace(",}","}");
                writer.write("N" + id + labels + properties + "\n");
            }
            
            //process edges
            Iterator<PGEdge> ite = this.edges.iterator();
            while(ite.hasNext()){
                edge = ite.next();
                //process labels
                labels = "[";
                itl = edge.getLabels();
                while(itl.hasNext()){
                    labels = labels + itl.next();
                }
                labels = labels + "]";
                labels = labels.replace(",]","]");
                //process (node) properties 
                properties = "{";
                itp = edge.getProperties();
                while(itp.hasNext()){
                    prop = itp.next();
                    properties = properties + prop.getLabel() + ":\"" + prop.getValue() + "\",";
                }
                properties = properties + "}";
                properties = properties.replace(",}","}");
                //process source and target nodes
                source_id = "(" + edge.getSourceNode().getId() + ")";
                target_id = "(" + edge.getTargetNode().getId() + ")";
                writer.write("E" + source_id + target_id + labels + properties + "\n");
            }
            
            
            writer.close();
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        
        
    }
    
    /*
    public void addNode(Node node){
        if(!nodes.contains(node)){
            nodes.add(node);
            node.setId(id++);
            Iterator<Property> it = node.getProperties();
            while(it.hasNext()){
                Property pt = it.next();
                this.addProperty(pt);
            }
        }
    }*/
    
    /*
    public void AddEdge(Edge edge){
        if(!edges.contains(edge)){
            edges.add(edge);
            this.addNode(edge.getSourceNode());
            this.addNode(edge.getTargetNode());            
            Iterator<Property> it = edge.getProperties();
            while(it.hasNext()){
                Property pt = it.next();
                this.addProperty(pt);
            }
        }
    }*/
    
    /*
    private void addProperty(Property property){
        if(!propertys.contains(property)){
            propertys.add(property);
            property.setId(id++);
        }
    }*/

    
}

