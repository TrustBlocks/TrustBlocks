// Compiled by ClojureScript 1.10.844 {:target :nodejs, :nodejs-rt true}
goog.provide('cljs.repl');
goog.require('cljs.core');
goog.require('cljs.spec.alpha');
goog.require('goog.string');
goog.require('goog.string.format');
cljs.repl.print_doc = (function cljs$repl$print_doc(p__8589){
var map__8590 = p__8589;
var map__8590__$1 = cljs.core.__destructure_map.call(null,map__8590);
var m = map__8590__$1;
var n = cljs.core.get.call(null,map__8590__$1,new cljs.core.Keyword(null,"ns","ns",441598760));
var nm = cljs.core.get.call(null,map__8590__$1,new cljs.core.Keyword(null,"name","name",1843675177));
cljs.core.println.call(null,"-------------------------");

cljs.core.println.call(null,(function (){var or__4160__auto__ = new cljs.core.Keyword(null,"spec","spec",347520401).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_(or__4160__auto__)){
return or__4160__auto__;
} else {
return [(function (){var temp__5753__auto__ = new cljs.core.Keyword(null,"ns","ns",441598760).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_(temp__5753__auto__)){
var ns = temp__5753__auto__;
return [cljs.core.str.cljs$core$IFn$_invoke$arity$1(ns),"/"].join('');
} else {
return null;
}
})(),cljs.core.str.cljs$core$IFn$_invoke$arity$1(new cljs.core.Keyword(null,"name","name",1843675177).cljs$core$IFn$_invoke$arity$1(m))].join('');
}
})());

if(cljs.core.truth_(new cljs.core.Keyword(null,"protocol","protocol",652470118).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"Protocol");
} else {
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"forms","forms",2045992350).cljs$core$IFn$_invoke$arity$1(m))){
var seq__8591_8619 = cljs.core.seq.call(null,new cljs.core.Keyword(null,"forms","forms",2045992350).cljs$core$IFn$_invoke$arity$1(m));
var chunk__8592_8620 = null;
var count__8593_8621 = (0);
var i__8594_8622 = (0);
while(true){
if((i__8594_8622 < count__8593_8621)){
var f_8623 = cljs.core._nth.call(null,chunk__8592_8620,i__8594_8622);
cljs.core.println.call(null,"  ",f_8623);


var G__8624 = seq__8591_8619;
var G__8625 = chunk__8592_8620;
var G__8626 = count__8593_8621;
var G__8627 = (i__8594_8622 + (1));
seq__8591_8619 = G__8624;
chunk__8592_8620 = G__8625;
count__8593_8621 = G__8626;
i__8594_8622 = G__8627;
continue;
} else {
var temp__5753__auto___8628 = cljs.core.seq.call(null,seq__8591_8619);
if(temp__5753__auto___8628){
var seq__8591_8629__$1 = temp__5753__auto___8628;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__8591_8629__$1)){
var c__4591__auto___8630 = cljs.core.chunk_first.call(null,seq__8591_8629__$1);
var G__8631 = cljs.core.chunk_rest.call(null,seq__8591_8629__$1);
var G__8632 = c__4591__auto___8630;
var G__8633 = cljs.core.count.call(null,c__4591__auto___8630);
var G__8634 = (0);
seq__8591_8619 = G__8631;
chunk__8592_8620 = G__8632;
count__8593_8621 = G__8633;
i__8594_8622 = G__8634;
continue;
} else {
var f_8635 = cljs.core.first.call(null,seq__8591_8629__$1);
cljs.core.println.call(null,"  ",f_8635);


var G__8636 = cljs.core.next.call(null,seq__8591_8629__$1);
var G__8637 = null;
var G__8638 = (0);
var G__8639 = (0);
seq__8591_8619 = G__8636;
chunk__8592_8620 = G__8637;
count__8593_8621 = G__8638;
i__8594_8622 = G__8639;
continue;
}
} else {
}
}
break;
}
} else {
if(cljs.core.truth_(new cljs.core.Keyword(null,"arglists","arglists",1661989754).cljs$core$IFn$_invoke$arity$1(m))){
var arglists_8640 = new cljs.core.Keyword(null,"arglists","arglists",1661989754).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_((function (){var or__4160__auto__ = new cljs.core.Keyword(null,"macro","macro",-867863404).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_(or__4160__auto__)){
return or__4160__auto__;
} else {
return new cljs.core.Keyword(null,"repl-special-function","repl-special-function",1262603725).cljs$core$IFn$_invoke$arity$1(m);
}
})())){
cljs.core.prn.call(null,arglists_8640);
} else {
cljs.core.prn.call(null,((cljs.core._EQ_.call(null,new cljs.core.Symbol(null,"quote","quote",1377916282,null),cljs.core.first.call(null,arglists_8640)))?cljs.core.second.call(null,arglists_8640):arglists_8640));
}
} else {
}
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"special-form","special-form",-1326536374).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"Special Form");

cljs.core.println.call(null," ",new cljs.core.Keyword(null,"doc","doc",1913296891).cljs$core$IFn$_invoke$arity$1(m));

if(cljs.core.contains_QMARK_.call(null,m,new cljs.core.Keyword(null,"url","url",276297046))){
if(cljs.core.truth_(new cljs.core.Keyword(null,"url","url",276297046).cljs$core$IFn$_invoke$arity$1(m))){
return cljs.core.println.call(null,["\n  Please see http://clojure.org/",cljs.core.str.cljs$core$IFn$_invoke$arity$1(new cljs.core.Keyword(null,"url","url",276297046).cljs$core$IFn$_invoke$arity$1(m))].join(''));
} else {
return null;
}
} else {
return cljs.core.println.call(null,["\n  Please see http://clojure.org/special_forms#",cljs.core.str.cljs$core$IFn$_invoke$arity$1(new cljs.core.Keyword(null,"name","name",1843675177).cljs$core$IFn$_invoke$arity$1(m))].join(''));
}
} else {
if(cljs.core.truth_(new cljs.core.Keyword(null,"macro","macro",-867863404).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"Macro");
} else {
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"spec","spec",347520401).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"Spec");
} else {
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"repl-special-function","repl-special-function",1262603725).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"REPL Special Function");
} else {
}

cljs.core.println.call(null," ",new cljs.core.Keyword(null,"doc","doc",1913296891).cljs$core$IFn$_invoke$arity$1(m));

if(cljs.core.truth_(new cljs.core.Keyword(null,"protocol","protocol",652470118).cljs$core$IFn$_invoke$arity$1(m))){
var seq__8595_8641 = cljs.core.seq.call(null,new cljs.core.Keyword(null,"methods","methods",453930866).cljs$core$IFn$_invoke$arity$1(m));
var chunk__8596_8642 = null;
var count__8597_8643 = (0);
var i__8598_8644 = (0);
while(true){
if((i__8598_8644 < count__8597_8643)){
var vec__8607_8645 = cljs.core._nth.call(null,chunk__8596_8642,i__8598_8644);
var name_8646 = cljs.core.nth.call(null,vec__8607_8645,(0),null);
var map__8610_8647 = cljs.core.nth.call(null,vec__8607_8645,(1),null);
var map__8610_8648__$1 = cljs.core.__destructure_map.call(null,map__8610_8647);
var doc_8649 = cljs.core.get.call(null,map__8610_8648__$1,new cljs.core.Keyword(null,"doc","doc",1913296891));
var arglists_8650 = cljs.core.get.call(null,map__8610_8648__$1,new cljs.core.Keyword(null,"arglists","arglists",1661989754));
cljs.core.println.call(null);

cljs.core.println.call(null," ",name_8646);

cljs.core.println.call(null," ",arglists_8650);

if(cljs.core.truth_(doc_8649)){
cljs.core.println.call(null," ",doc_8649);
} else {
}


var G__8651 = seq__8595_8641;
var G__8652 = chunk__8596_8642;
var G__8653 = count__8597_8643;
var G__8654 = (i__8598_8644 + (1));
seq__8595_8641 = G__8651;
chunk__8596_8642 = G__8652;
count__8597_8643 = G__8653;
i__8598_8644 = G__8654;
continue;
} else {
var temp__5753__auto___8655 = cljs.core.seq.call(null,seq__8595_8641);
if(temp__5753__auto___8655){
var seq__8595_8656__$1 = temp__5753__auto___8655;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__8595_8656__$1)){
var c__4591__auto___8657 = cljs.core.chunk_first.call(null,seq__8595_8656__$1);
var G__8658 = cljs.core.chunk_rest.call(null,seq__8595_8656__$1);
var G__8659 = c__4591__auto___8657;
var G__8660 = cljs.core.count.call(null,c__4591__auto___8657);
var G__8661 = (0);
seq__8595_8641 = G__8658;
chunk__8596_8642 = G__8659;
count__8597_8643 = G__8660;
i__8598_8644 = G__8661;
continue;
} else {
var vec__8611_8662 = cljs.core.first.call(null,seq__8595_8656__$1);
var name_8663 = cljs.core.nth.call(null,vec__8611_8662,(0),null);
var map__8614_8664 = cljs.core.nth.call(null,vec__8611_8662,(1),null);
var map__8614_8665__$1 = cljs.core.__destructure_map.call(null,map__8614_8664);
var doc_8666 = cljs.core.get.call(null,map__8614_8665__$1,new cljs.core.Keyword(null,"doc","doc",1913296891));
var arglists_8667 = cljs.core.get.call(null,map__8614_8665__$1,new cljs.core.Keyword(null,"arglists","arglists",1661989754));
cljs.core.println.call(null);

cljs.core.println.call(null," ",name_8663);

cljs.core.println.call(null," ",arglists_8667);

if(cljs.core.truth_(doc_8666)){
cljs.core.println.call(null," ",doc_8666);
} else {
}


var G__8668 = cljs.core.next.call(null,seq__8595_8656__$1);
var G__8669 = null;
var G__8670 = (0);
var G__8671 = (0);
seq__8595_8641 = G__8668;
chunk__8596_8642 = G__8669;
count__8597_8643 = G__8670;
i__8598_8644 = G__8671;
continue;
}
} else {
}
}
break;
}
} else {
}

if(cljs.core.truth_(n)){
var temp__5753__auto__ = cljs.spec.alpha.get_spec.call(null,cljs.core.symbol.call(null,cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.ns_name.call(null,n)),cljs.core.name.call(null,nm)));
if(cljs.core.truth_(temp__5753__auto__)){
var fnspec = temp__5753__auto__;
cljs.core.print.call(null,"Spec");

var seq__8615 = cljs.core.seq.call(null,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"args","args",1315556576),new cljs.core.Keyword(null,"ret","ret",-468222814),new cljs.core.Keyword(null,"fn","fn",-1175266204)], null));
var chunk__8616 = null;
var count__8617 = (0);
var i__8618 = (0);
while(true){
if((i__8618 < count__8617)){
var role = cljs.core._nth.call(null,chunk__8616,i__8618);
var temp__5753__auto___8672__$1 = cljs.core.get.call(null,fnspec,role);
if(cljs.core.truth_(temp__5753__auto___8672__$1)){
var spec_8673 = temp__5753__auto___8672__$1;
cljs.core.print.call(null,["\n ",cljs.core.name.call(null,role),":"].join(''),cljs.spec.alpha.describe.call(null,spec_8673));
} else {
}


var G__8674 = seq__8615;
var G__8675 = chunk__8616;
var G__8676 = count__8617;
var G__8677 = (i__8618 + (1));
seq__8615 = G__8674;
chunk__8616 = G__8675;
count__8617 = G__8676;
i__8618 = G__8677;
continue;
} else {
var temp__5753__auto____$1 = cljs.core.seq.call(null,seq__8615);
if(temp__5753__auto____$1){
var seq__8615__$1 = temp__5753__auto____$1;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__8615__$1)){
var c__4591__auto__ = cljs.core.chunk_first.call(null,seq__8615__$1);
var G__8678 = cljs.core.chunk_rest.call(null,seq__8615__$1);
var G__8679 = c__4591__auto__;
var G__8680 = cljs.core.count.call(null,c__4591__auto__);
var G__8681 = (0);
seq__8615 = G__8678;
chunk__8616 = G__8679;
count__8617 = G__8680;
i__8618 = G__8681;
continue;
} else {
var role = cljs.core.first.call(null,seq__8615__$1);
var temp__5753__auto___8682__$2 = cljs.core.get.call(null,fnspec,role);
if(cljs.core.truth_(temp__5753__auto___8682__$2)){
var spec_8683 = temp__5753__auto___8682__$2;
cljs.core.print.call(null,["\n ",cljs.core.name.call(null,role),":"].join(''),cljs.spec.alpha.describe.call(null,spec_8683));
} else {
}


var G__8684 = cljs.core.next.call(null,seq__8615__$1);
var G__8685 = null;
var G__8686 = (0);
var G__8687 = (0);
seq__8615 = G__8684;
chunk__8616 = G__8685;
count__8617 = G__8686;
i__8618 = G__8687;
continue;
}
} else {
return null;
}
}
break;
}
} else {
return null;
}
} else {
return null;
}
}
});
/**
 * Constructs a data representation for a Error with keys:
 *  :cause - root cause message
 *  :phase - error phase
 *  :via - cause chain, with cause keys:
 *           :type - exception class symbol
 *           :message - exception message
 *           :data - ex-data
 *           :at - top stack element
 *  :trace - root cause stack elements
 */
cljs.repl.Error__GT_map = (function cljs$repl$Error__GT_map(o){
var base = (function (t){
return cljs.core.merge.call(null,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"type","type",1174270348),(((t instanceof cljs.core.ExceptionInfo))?new cljs.core.Symbol("cljs.core","ExceptionInfo","cljs.core/ExceptionInfo",701839050,null):(((t instanceof Error))?cljs.core.symbol.call(null,"js",t.name):null
))], null),(function (){var temp__5753__auto__ = cljs.core.ex_message.call(null,t);
if(cljs.core.truth_(temp__5753__auto__)){
var msg = temp__5753__auto__;
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"message","message",-406056002),msg], null);
} else {
return null;
}
})(),(function (){var temp__5753__auto__ = cljs.core.ex_data.call(null,t);
if(cljs.core.truth_(temp__5753__auto__)){
var ed = temp__5753__auto__;
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"data","data",-232669377),ed], null);
} else {
return null;
}
})());
});
var via = (function (){var via = cljs.core.PersistentVector.EMPTY;
var t = o;
while(true){
if(cljs.core.truth_(t)){
var G__8688 = cljs.core.conj.call(null,via,t);
var G__8689 = cljs.core.ex_cause.call(null,t);
via = G__8688;
t = G__8689;
continue;
} else {
return via;
}
break;
}
})();
var root = cljs.core.peek.call(null,via);
return cljs.core.merge.call(null,new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"via","via",-1904457336),cljs.core.vec.call(null,cljs.core.map.call(null,base,via)),new cljs.core.Keyword(null,"trace","trace",-1082747415),null], null),(function (){var temp__5753__auto__ = cljs.core.ex_message.call(null,root);
if(cljs.core.truth_(temp__5753__auto__)){
var root_msg = temp__5753__auto__;
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"cause","cause",231901252),root_msg], null);
} else {
return null;
}
})(),(function (){var temp__5753__auto__ = cljs.core.ex_data.call(null,root);
if(cljs.core.truth_(temp__5753__auto__)){
var data = temp__5753__auto__;
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"data","data",-232669377),data], null);
} else {
return null;
}
})(),(function (){var temp__5753__auto__ = new cljs.core.Keyword("clojure.error","phase","clojure.error/phase",275140358).cljs$core$IFn$_invoke$arity$1(cljs.core.ex_data.call(null,o));
if(cljs.core.truth_(temp__5753__auto__)){
var phase = temp__5753__auto__;
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"phase","phase",575722892),phase], null);
} else {
return null;
}
})());
});
/**
 * Returns an analysis of the phase, error, cause, and location of an error that occurred
 *   based on Throwable data, as returned by Throwable->map. All attributes other than phase
 *   are optional:
 *  :clojure.error/phase - keyword phase indicator, one of:
 *    :read-source :compile-syntax-check :compilation :macro-syntax-check :macroexpansion
 *    :execution :read-eval-result :print-eval-result
 *  :clojure.error/source - file name (no path)
 *  :clojure.error/line - integer line number
 *  :clojure.error/column - integer column number
 *  :clojure.error/symbol - symbol being expanded/compiled/invoked
 *  :clojure.error/class - cause exception class symbol
 *  :clojure.error/cause - cause exception message
 *  :clojure.error/spec - explain-data for spec error
 */
cljs.repl.ex_triage = (function cljs$repl$ex_triage(datafied_throwable){
var map__8692 = datafied_throwable;
var map__8692__$1 = cljs.core.__destructure_map.call(null,map__8692);
var via = cljs.core.get.call(null,map__8692__$1,new cljs.core.Keyword(null,"via","via",-1904457336));
var trace = cljs.core.get.call(null,map__8692__$1,new cljs.core.Keyword(null,"trace","trace",-1082747415));
var phase = cljs.core.get.call(null,map__8692__$1,new cljs.core.Keyword(null,"phase","phase",575722892),new cljs.core.Keyword(null,"execution","execution",253283524));
var map__8693 = cljs.core.last.call(null,via);
var map__8693__$1 = cljs.core.__destructure_map.call(null,map__8693);
var type = cljs.core.get.call(null,map__8693__$1,new cljs.core.Keyword(null,"type","type",1174270348));
var message = cljs.core.get.call(null,map__8693__$1,new cljs.core.Keyword(null,"message","message",-406056002));
var data = cljs.core.get.call(null,map__8693__$1,new cljs.core.Keyword(null,"data","data",-232669377));
var map__8694 = data;
var map__8694__$1 = cljs.core.__destructure_map.call(null,map__8694);
var problems = cljs.core.get.call(null,map__8694__$1,new cljs.core.Keyword("cljs.spec.alpha","problems","cljs.spec.alpha/problems",447400814));
var fn = cljs.core.get.call(null,map__8694__$1,new cljs.core.Keyword("cljs.spec.alpha","fn","cljs.spec.alpha/fn",408600443));
var caller = cljs.core.get.call(null,map__8694__$1,new cljs.core.Keyword("cljs.spec.test.alpha","caller","cljs.spec.test.alpha/caller",-398302390));
var map__8695 = new cljs.core.Keyword(null,"data","data",-232669377).cljs$core$IFn$_invoke$arity$1(cljs.core.first.call(null,via));
var map__8695__$1 = cljs.core.__destructure_map.call(null,map__8695);
var top_data = map__8695__$1;
var source = cljs.core.get.call(null,map__8695__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397));
return cljs.core.assoc.call(null,(function (){var G__8696 = phase;
var G__8696__$1 = (((G__8696 instanceof cljs.core.Keyword))?G__8696.fqn:null);
switch (G__8696__$1) {
case "read-source":
var map__8697 = data;
var map__8697__$1 = cljs.core.__destructure_map.call(null,map__8697);
var line = cljs.core.get.call(null,map__8697__$1,new cljs.core.Keyword("clojure.error","line","clojure.error/line",-1816287471));
var column = cljs.core.get.call(null,map__8697__$1,new cljs.core.Keyword("clojure.error","column","clojure.error/column",304721553));
var G__8698 = cljs.core.merge.call(null,new cljs.core.Keyword(null,"data","data",-232669377).cljs$core$IFn$_invoke$arity$1(cljs.core.second.call(null,via)),top_data);
var G__8698__$1 = (cljs.core.truth_(source)?cljs.core.assoc.call(null,G__8698,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397),source):G__8698);
var G__8698__$2 = (cljs.core.truth_(new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, ["NO_SOURCE_PATH",null,"NO_SOURCE_FILE",null], null), null).call(null,source))?cljs.core.dissoc.call(null,G__8698__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397)):G__8698__$1);
if(cljs.core.truth_(message)){
return cljs.core.assoc.call(null,G__8698__$2,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742),message);
} else {
return G__8698__$2;
}

break;
case "compile-syntax-check":
case "compilation":
case "macro-syntax-check":
case "macroexpansion":
var G__8699 = top_data;
var G__8699__$1 = (cljs.core.truth_(source)?cljs.core.assoc.call(null,G__8699,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397),source):G__8699);
var G__8699__$2 = (cljs.core.truth_(new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, ["NO_SOURCE_PATH",null,"NO_SOURCE_FILE",null], null), null).call(null,source))?cljs.core.dissoc.call(null,G__8699__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397)):G__8699__$1);
var G__8699__$3 = (cljs.core.truth_(type)?cljs.core.assoc.call(null,G__8699__$2,new cljs.core.Keyword("clojure.error","class","clojure.error/class",278435890),type):G__8699__$2);
var G__8699__$4 = (cljs.core.truth_(message)?cljs.core.assoc.call(null,G__8699__$3,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742),message):G__8699__$3);
if(cljs.core.truth_(problems)){
return cljs.core.assoc.call(null,G__8699__$4,new cljs.core.Keyword("clojure.error","spec","clojure.error/spec",2055032595),data);
} else {
return G__8699__$4;
}

break;
case "read-eval-result":
case "print-eval-result":
var vec__8700 = cljs.core.first.call(null,trace);
var source__$1 = cljs.core.nth.call(null,vec__8700,(0),null);
var method = cljs.core.nth.call(null,vec__8700,(1),null);
var file = cljs.core.nth.call(null,vec__8700,(2),null);
var line = cljs.core.nth.call(null,vec__8700,(3),null);
var G__8703 = top_data;
var G__8703__$1 = (cljs.core.truth_(line)?cljs.core.assoc.call(null,G__8703,new cljs.core.Keyword("clojure.error","line","clojure.error/line",-1816287471),line):G__8703);
var G__8703__$2 = (cljs.core.truth_(file)?cljs.core.assoc.call(null,G__8703__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397),file):G__8703__$1);
var G__8703__$3 = (cljs.core.truth_((function (){var and__4149__auto__ = source__$1;
if(cljs.core.truth_(and__4149__auto__)){
return method;
} else {
return and__4149__auto__;
}
})())?cljs.core.assoc.call(null,G__8703__$2,new cljs.core.Keyword("clojure.error","symbol","clojure.error/symbol",1544821994),(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[source__$1,method],null))):G__8703__$2);
var G__8703__$4 = (cljs.core.truth_(type)?cljs.core.assoc.call(null,G__8703__$3,new cljs.core.Keyword("clojure.error","class","clojure.error/class",278435890),type):G__8703__$3);
if(cljs.core.truth_(message)){
return cljs.core.assoc.call(null,G__8703__$4,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742),message);
} else {
return G__8703__$4;
}

break;
case "execution":
var vec__8704 = cljs.core.first.call(null,trace);
var source__$1 = cljs.core.nth.call(null,vec__8704,(0),null);
var method = cljs.core.nth.call(null,vec__8704,(1),null);
var file = cljs.core.nth.call(null,vec__8704,(2),null);
var line = cljs.core.nth.call(null,vec__8704,(3),null);
var file__$1 = cljs.core.first.call(null,cljs.core.remove.call(null,(function (p1__8691_SHARP_){
var or__4160__auto__ = (p1__8691_SHARP_ == null);
if(or__4160__auto__){
return or__4160__auto__;
} else {
return new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, ["NO_SOURCE_PATH",null,"NO_SOURCE_FILE",null], null), null).call(null,p1__8691_SHARP_);
}
}),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"file","file",-1269645878).cljs$core$IFn$_invoke$arity$1(caller),file], null)));
var err_line = (function (){var or__4160__auto__ = new cljs.core.Keyword(null,"line","line",212345235).cljs$core$IFn$_invoke$arity$1(caller);
if(cljs.core.truth_(or__4160__auto__)){
return or__4160__auto__;
} else {
return line;
}
})();
var G__8707 = new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword("clojure.error","class","clojure.error/class",278435890),type], null);
var G__8707__$1 = (cljs.core.truth_(err_line)?cljs.core.assoc.call(null,G__8707,new cljs.core.Keyword("clojure.error","line","clojure.error/line",-1816287471),err_line):G__8707);
var G__8707__$2 = (cljs.core.truth_(message)?cljs.core.assoc.call(null,G__8707__$1,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742),message):G__8707__$1);
var G__8707__$3 = (cljs.core.truth_((function (){var or__4160__auto__ = fn;
if(cljs.core.truth_(or__4160__auto__)){
return or__4160__auto__;
} else {
var and__4149__auto__ = source__$1;
if(cljs.core.truth_(and__4149__auto__)){
return method;
} else {
return and__4149__auto__;
}
}
})())?cljs.core.assoc.call(null,G__8707__$2,new cljs.core.Keyword("clojure.error","symbol","clojure.error/symbol",1544821994),(function (){var or__4160__auto__ = fn;
if(cljs.core.truth_(or__4160__auto__)){
return or__4160__auto__;
} else {
return (new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[source__$1,method],null));
}
})()):G__8707__$2);
var G__8707__$4 = (cljs.core.truth_(file__$1)?cljs.core.assoc.call(null,G__8707__$3,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397),file__$1):G__8707__$3);
if(cljs.core.truth_(problems)){
return cljs.core.assoc.call(null,G__8707__$4,new cljs.core.Keyword("clojure.error","spec","clojure.error/spec",2055032595),data);
} else {
return G__8707__$4;
}

break;
default:
throw (new Error(["No matching clause: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(G__8696__$1)].join('')));

}
})(),new cljs.core.Keyword("clojure.error","phase","clojure.error/phase",275140358),phase);
});
/**
 * Returns a string from exception data, as produced by ex-triage.
 *   The first line summarizes the exception phase and location.
 *   The subsequent lines describe the cause.
 */
cljs.repl.ex_str = (function cljs$repl$ex_str(p__8711){
var map__8712 = p__8711;
var map__8712__$1 = cljs.core.__destructure_map.call(null,map__8712);
var triage_data = map__8712__$1;
var phase = cljs.core.get.call(null,map__8712__$1,new cljs.core.Keyword("clojure.error","phase","clojure.error/phase",275140358));
var source = cljs.core.get.call(null,map__8712__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397));
var line = cljs.core.get.call(null,map__8712__$1,new cljs.core.Keyword("clojure.error","line","clojure.error/line",-1816287471));
var column = cljs.core.get.call(null,map__8712__$1,new cljs.core.Keyword("clojure.error","column","clojure.error/column",304721553));
var symbol = cljs.core.get.call(null,map__8712__$1,new cljs.core.Keyword("clojure.error","symbol","clojure.error/symbol",1544821994));
var class$ = cljs.core.get.call(null,map__8712__$1,new cljs.core.Keyword("clojure.error","class","clojure.error/class",278435890));
var cause = cljs.core.get.call(null,map__8712__$1,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742));
var spec = cljs.core.get.call(null,map__8712__$1,new cljs.core.Keyword("clojure.error","spec","clojure.error/spec",2055032595));
var loc = [cljs.core.str.cljs$core$IFn$_invoke$arity$1((function (){var or__4160__auto__ = source;
if(cljs.core.truth_(or__4160__auto__)){
return or__4160__auto__;
} else {
return "<cljs repl>";
}
})()),":",cljs.core.str.cljs$core$IFn$_invoke$arity$1((function (){var or__4160__auto__ = line;
if(cljs.core.truth_(or__4160__auto__)){
return or__4160__auto__;
} else {
return (1);
}
})()),(cljs.core.truth_(column)?[":",cljs.core.str.cljs$core$IFn$_invoke$arity$1(column)].join(''):"")].join('');
var class_name = cljs.core.name.call(null,(function (){var or__4160__auto__ = class$;
if(cljs.core.truth_(or__4160__auto__)){
return or__4160__auto__;
} else {
return "";
}
})());
var simple_class = class_name;
var cause_type = ((cljs.core.contains_QMARK_.call(null,new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, ["RuntimeException",null,"Exception",null], null), null),simple_class))?"":[" (",simple_class,")"].join(''));
var format = goog.string.format;
var G__8713 = phase;
var G__8713__$1 = (((G__8713 instanceof cljs.core.Keyword))?G__8713.fqn:null);
switch (G__8713__$1) {
case "read-source":
return format.call(null,"Syntax error reading source at (%s).\n%s\n",loc,cause);

break;
case "macro-syntax-check":
return format.call(null,"Syntax error macroexpanding %sat (%s).\n%s",(cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):""),loc,(cljs.core.truth_(spec)?(function (){var sb__4702__auto__ = (new goog.string.StringBuffer());
var _STAR_print_newline_STAR__orig_val__8714_8723 = cljs.core._STAR_print_newline_STAR_;
var _STAR_print_fn_STAR__orig_val__8715_8724 = cljs.core._STAR_print_fn_STAR_;
var _STAR_print_newline_STAR__temp_val__8716_8725 = true;
var _STAR_print_fn_STAR__temp_val__8717_8726 = (function (x__4703__auto__){
return sb__4702__auto__.append(x__4703__auto__);
});
(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__temp_val__8716_8725);

(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__temp_val__8717_8726);

try{cljs.spec.alpha.explain_out.call(null,cljs.core.update.call(null,spec,new cljs.core.Keyword("cljs.spec.alpha","problems","cljs.spec.alpha/problems",447400814),(function (probs){
return cljs.core.map.call(null,(function (p1__8709_SHARP_){
return cljs.core.dissoc.call(null,p1__8709_SHARP_,new cljs.core.Keyword(null,"in","in",-1531184865));
}),probs);
}))
);
}finally {(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__orig_val__8715_8724);

(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__orig_val__8714_8723);
}
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(sb__4702__auto__);
})():format.call(null,"%s\n",cause)));

break;
case "macroexpansion":
return format.call(null,"Unexpected error%s macroexpanding %sat (%s).\n%s\n",cause_type,(cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):""),loc,cause);

break;
case "compile-syntax-check":
return format.call(null,"Syntax error%s compiling %sat (%s).\n%s\n",cause_type,(cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):""),loc,cause);

break;
case "compilation":
return format.call(null,"Unexpected error%s compiling %sat (%s).\n%s\n",cause_type,(cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):""),loc,cause);

break;
case "read-eval-result":
return format.call(null,"Error reading eval result%s at %s (%s).\n%s\n",cause_type,symbol,loc,cause);

break;
case "print-eval-result":
return format.call(null,"Error printing return value%s at %s (%s).\n%s\n",cause_type,symbol,loc,cause);

break;
case "execution":
if(cljs.core.truth_(spec)){
return format.call(null,"Execution error - invalid arguments to %s at (%s).\n%s",symbol,loc,(function (){var sb__4702__auto__ = (new goog.string.StringBuffer());
var _STAR_print_newline_STAR__orig_val__8718_8727 = cljs.core._STAR_print_newline_STAR_;
var _STAR_print_fn_STAR__orig_val__8719_8728 = cljs.core._STAR_print_fn_STAR_;
var _STAR_print_newline_STAR__temp_val__8720_8729 = true;
var _STAR_print_fn_STAR__temp_val__8721_8730 = (function (x__4703__auto__){
return sb__4702__auto__.append(x__4703__auto__);
});
(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__temp_val__8720_8729);

(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__temp_val__8721_8730);

try{cljs.spec.alpha.explain_out.call(null,cljs.core.update.call(null,spec,new cljs.core.Keyword("cljs.spec.alpha","problems","cljs.spec.alpha/problems",447400814),(function (probs){
return cljs.core.map.call(null,(function (p1__8710_SHARP_){
return cljs.core.dissoc.call(null,p1__8710_SHARP_,new cljs.core.Keyword(null,"in","in",-1531184865));
}),probs);
}))
);
}finally {(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__orig_val__8719_8728);

(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__orig_val__8718_8727);
}
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(sb__4702__auto__);
})());
} else {
return format.call(null,"Execution error%s at %s(%s).\n%s\n",cause_type,(cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):""),loc,cause);
}

break;
default:
throw (new Error(["No matching clause: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(G__8713__$1)].join('')));

}
});
cljs.repl.error__GT_str = (function cljs$repl$error__GT_str(error){
return cljs.repl.ex_str.call(null,cljs.repl.ex_triage.call(null,cljs.repl.Error__GT_map.call(null,error)));
});

//# sourceMappingURL=repl.js.map
