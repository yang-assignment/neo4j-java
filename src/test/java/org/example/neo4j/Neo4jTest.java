package org.example.neo4j;

import org.junit.Test;
import org.neo4j.driver.*;
public class Neo4jTest {
    private static Session session = GraphDatabase.driver("bolt://192.168.81.140:7687",
            AuthTokens.basic("neo4j","123456")).session();
    /*
     * 查看数据库是否有数据
     */
    @Test
    public void matchReturnTest(){
        Result result = session.run("match (n) return n");
        while (result.hasNext()){
            Record record = result.next();
            System.out.println(record);
        }
        session.close();
    }


    /*
     *创建节点
     */
    @Test
    public void createTest() {
        session.run("create (Dav:Person{name:'David',age:20,hobby:'painting',gender:'male'})");
        session.run("create (Jor:Person{name:'Jordan',age:22,hobby:'playing basketball',gender:'male'})");
        session.run("create (Gra:Person{name:'Grace',age:18,hobby:'singing',gender:'female'})");
        session.run("create (Jac:Person{name:'Jack',age:20,hobby:'swimming',gender:'male'})-[friend:Likes]->(Emm:Person{name:'Emma',age:18,hobby:'reading novels',gender:'female'})");
        session.close();
    }



    /*
     *按条件查询节点详细信息
     */
    @Test
    public void whereTest() {
        Result result = session.run("match (p:Person) where p.gender='male' return p.name,p.age,p.hobby,p.gender");
        while (result.hasNext()) {
            Record record = result.next();
            System.out.println(record);
        }
        session.close();
    }
    /*
     * 删除节点及相关联的关系
     */
    @Test
    public void deleteTest() {
        session.run("match (Jac:Person)-[friend]->(Emm:Person) delete Jac,Emm,friend");
        Result result = session.run("match (p:Person) return p.name");
        while (result.hasNext()) {
            Record record = result.next();
            System.out.println(record);
        }
        session.close();
    }

    /*
     * 移除属性
     */
    @Test
    public void removeTest() {
        Result result = session.run("match (p{name:'David'}) remove p.hobby return p.name,p.age,p.hobby,p.gender");
        while (result.hasNext()) {
            Record record = result.next();
            System.out.println(record);
        }
        session.close();
    }


}
