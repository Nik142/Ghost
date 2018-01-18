package com.google.engedu.ghost;

import java.util.HashMap;
import java.util.Random;


public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
        HashMap<String, TrieNode> children = this.children;

        for(int i=0;i<s.length();i++){

            String c = String.valueOf(s.charAt(i));
            TrieNode t;
            if(!children.containsKey(c))
            {
                t=new TrieNode();
                children.put(c,t);
            }
            else
            {
                t=children.get(c);
            }
            children=t.children;
            if(i == s.length() - 1){
                t.isWord = true;
            }
        }
    }

    public boolean isWord(String s) {
        HashMap<String, TrieNode> children = this.children;

        TrieNode t = new TrieNode();
        for(int i=0;i<s.length();i++)
        {
            String c = String.valueOf(s.charAt(i));
            if(children.containsKey(c))
            {
                t=children.get(c);
                children=t.children;

            }
            else return  false;
        }

        if(t.isWord){
            return true;
        }
        else {
            return false;
        }

    }

    public String getAnyWordStartingWith(String s) {

        HashMap<String, TrieNode> children = this.children;
        TrieNode t = new TrieNode();
        String str = "";
        int i;

        if(s == ""){
            Object[] values = children.keySet().toArray();
            str = (String) values[0];
            s = s.concat(str);
        }
        else{
            for(i=0;i<s.length();i++){
                String c = String.valueOf(s.charAt(i));
                if(children.containsKey(c))
                {
                    t = children.get(c);
                    children = t.children;
                }
                else return null;
            }

            if(i == s.length()){
                while (t.isWord == false){
                    Object[] values = t.children.keySet().toArray();
                    str = (String) values[0];
                    s = s.concat(str);
                    t = children.get(str);
                    children = t.children;
                }
            }
        }

        return s;
    }

    public String getGoodWordStartingWith(String s) {

        HashMap<String, TrieNode> children = this.children;
        TrieNode t = new TrieNode();
        Random random = new Random();
        String str = "";
        int i;

        if(s == ""){
            Object[] values = children.keySet().toArray();
            str = (String) values[random.nextInt(values.length)];
            s = s.concat(str);
        }
        else{for(i=0;i<s.length();i++){
            String c = String.valueOf(s.charAt(i));
            if(children.containsKey(c))
            {
                t=children.get(c);
                children=t.children;
            }
            else return  null;}

            while(!t.isWord){
        Object[] values = t.children.keySet().toArray();

                str = (String) values[random.nextInt(values.length)];
                t = children.get(str);
                s = s.concat(str);
                children = t.children;
            }}

       return s;
    }
}
