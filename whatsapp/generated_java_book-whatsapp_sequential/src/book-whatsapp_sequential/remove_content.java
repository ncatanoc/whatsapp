package book-whatsapp_sequential; 

import eventb_prelude.*;
import Util.Utilities;

public class remove_content{
	/*@ spec_public */ private machine2 machine; // reference to the machine 

	/*@ public normal_behavior
		requires true;
		assignable \everything;
		ensures this.machine == m; */
	public remove_content(machine2 m) {
		this.machine = m;
	}

	/*@ public normal_behavior
		requires true;
 		assignable \nothing;
		ensures \result <==> (machine.get_user().has(u1) && machine.get_user().has(u2) && machine.get_active().has(new Pair<Integer,Integer>(u1,u2)) && machine.get_chatcontent().domain().has(u1) && machine.get_chatcontent().apply(u1).domain().has(c) && machine.get_chatcontent().apply(u1).apply(c).has(u2)); */
	public /*@ pure */ boolean guard_remove_content( Integer c, Integer u1, Integer u2) {
		return (machine.get_user().has(u1) && machine.get_user().has(u2) && machine.get_active().has(new Pair<Integer,Integer>(u1,u2)) && machine.get_chatcontent().domain().has(u1) && machine.get_chatcontent().apply(u1).domain().has(c) && machine.get_chatcontent().apply(u1).apply(c).has(u2));
	}

	/*@ public normal_behavior
		requires guard_remove_content(c,u1,u2);
		assignable machine.chatcontent;
		ensures guard_remove_content(c,u1,u2) &&  machine.get_chatcontent().equals(\old((machine.get_chatcontent().override(new BRelation<Integer,BRelation<Integer,BSet<Integer>>>(new Pair<Integer,BRelation<Integer,BSet<Integer>>>(u1,machine.get_chatcontent().apply(u1).domainSubtraction(new BSet<Integer>(c)))))))); 
	 also
		requires !guard_remove_content(c,u1,u2);
		assignable \nothing;
		ensures true; */
	public void run_remove_content( Integer c, Integer u1, Integer u2){
		if(guard_remove_content(c,u1,u2)) {
			BRelation<Integer,BRelation<Integer,BSet<Integer>>> chatcontent_tmp = machine.get_chatcontent();

			machine.set_chatcontent((chatcontent_tmp.override(new BRelation<Integer,BRelation<Integer,BSet<Integer>>>(new Pair<Integer,BRelation<Integer,BSet<Integer>>>(u1,chatcontent_tmp.apply(u1).domainSubtraction(new BSet<Integer>(c)))))));

			System.out.println("remove_content executed c: " + c + " u1: " + u1 + " u2: " + u2 + " ");
		}
	}

}
