package android.support.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import android.view.ViewGroup;
import java.io.IOException;
import java.lang.reflect.Constructor;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class TransitionInflater {
    private static final Class<?>[] a = {Context.class, AttributeSet.class};
    private static final ArrayMap<String, Constructor> b = new ArrayMap<>();
    private final Context c;

    private TransitionInflater(@NonNull Context context) {
        this.c = context;
    }

    public static TransitionInflater from(Context context) {
        return new TransitionInflater(context);
    }

    public Transition inflateTransition(int i) {
        XmlResourceParser xml = this.c.getResources().getXml(i);
        try {
            Transition a2 = a((XmlPullParser) xml, Xml.asAttributeSet(xml), (Transition) null);
            xml.close();
            return a2;
        } catch (XmlPullParserException e) {
            throw new InflateException(e.getMessage(), e);
        } catch (IOException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append(xml.getPositionDescription());
            sb.append(": ");
            sb.append(e2.getMessage());
            throw new InflateException(sb.toString(), e2);
        } catch (Throwable th) {
            xml.close();
            throw th;
        }
    }

    public TransitionManager inflateTransitionManager(int i, ViewGroup viewGroup) {
        XmlResourceParser xml = this.c.getResources().getXml(i);
        try {
            TransitionManager a2 = a((XmlPullParser) xml, Xml.asAttributeSet(xml), viewGroup);
            xml.close();
            return a2;
        } catch (XmlPullParserException e) {
            InflateException inflateException = new InflateException(e.getMessage());
            inflateException.initCause(e);
            throw inflateException;
        } catch (IOException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append(xml.getPositionDescription());
            sb.append(": ");
            sb.append(e2.getMessage());
            InflateException inflateException2 = new InflateException(sb.toString());
            inflateException2.initCause(e2);
            throw inflateException2;
        } catch (Throwable th) {
            xml.close();
            throw th;
        }
    }

    private Transition a(XmlPullParser xmlPullParser, AttributeSet attributeSet, Transition transition) {
        Transition transition2;
        int depth = xmlPullParser.getDepth();
        TransitionSet transitionSet = transition instanceof TransitionSet ? (TransitionSet) transition : null;
        loop0:
        while (true) {
            transition2 = null;
            while (true) {
                int next = xmlPullParser.next();
                if ((next != 3 || xmlPullParser.getDepth() > depth) && next != 1) {
                    if (next == 2) {
                        String name = xmlPullParser.getName();
                        if ("fade".equals(name)) {
                            transition2 = new Fade(this.c, attributeSet);
                        } else if ("changeBounds".equals(name)) {
                            transition2 = new ChangeBounds(this.c, attributeSet);
                        } else if ("slide".equals(name)) {
                            transition2 = new Slide(this.c, attributeSet);
                        } else if ("explode".equals(name)) {
                            transition2 = new Explode(this.c, attributeSet);
                        } else if ("changeImageTransform".equals(name)) {
                            transition2 = new ChangeImageTransform(this.c, attributeSet);
                        } else if ("changeTransform".equals(name)) {
                            transition2 = new ChangeTransform(this.c, attributeSet);
                        } else if ("changeClipBounds".equals(name)) {
                            transition2 = new ChangeClipBounds(this.c, attributeSet);
                        } else if ("autoTransition".equals(name)) {
                            transition2 = new AutoTransition(this.c, attributeSet);
                        } else if ("changeScroll".equals(name)) {
                            transition2 = new ChangeScroll(this.c, attributeSet);
                        } else if ("transitionSet".equals(name)) {
                            transition2 = new TransitionSet(this.c, attributeSet);
                        } else if ("transition".equals(name)) {
                            transition2 = (Transition) a(attributeSet, Transition.class, "transition");
                        } else if ("targets".equals(name)) {
                            b(xmlPullParser, attributeSet, transition);
                        } else if ("arcMotion".equals(name)) {
                            if (transition == null) {
                                throw new RuntimeException("Invalid use of arcMotion element");
                            }
                            transition.setPathMotion(new ArcMotion(this.c, attributeSet));
                        } else if ("pathMotion".equals(name)) {
                            if (transition == null) {
                                throw new RuntimeException("Invalid use of pathMotion element");
                            }
                            transition.setPathMotion((PathMotion) a(attributeSet, PathMotion.class, "pathMotion"));
                        } else if (!"patternPathMotion".equals(name)) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Unknown scene name: ");
                            sb.append(xmlPullParser.getName());
                            throw new RuntimeException(sb.toString());
                        } else if (transition == null) {
                            throw new RuntimeException("Invalid use of patternPathMotion element");
                        } else {
                            transition.setPathMotion(new PatternPathMotion(this.c, attributeSet));
                        }
                        if (transition2 == null) {
                            continue;
                        } else {
                            if (!xmlPullParser.isEmptyElementTag()) {
                                a(xmlPullParser, attributeSet, transition2);
                            }
                            if (transitionSet != null) {
                                break;
                            } else if (transition != null) {
                                throw new InflateException("Could not add transition to another transition.");
                            }
                        }
                    }
                }
            }
            transitionSet.addTransition(transition2);
        }
        return transition2;
    }

    private Object a(AttributeSet attributeSet, Class cls, String str) {
        Object newInstance;
        String attributeValue = attributeSet.getAttributeValue(null, "class");
        if (attributeValue == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" tag must have a 'class' attribute");
            throw new InflateException(sb.toString());
        }
        try {
            synchronized (b) {
                Constructor constructor = (Constructor) b.get(attributeValue);
                if (constructor == null) {
                    Class asSubclass = this.c.getClassLoader().loadClass(attributeValue).asSubclass(cls);
                    if (asSubclass != null) {
                        constructor = asSubclass.getConstructor(a);
                        constructor.setAccessible(true);
                        b.put(attributeValue, constructor);
                    }
                }
                newInstance = constructor.newInstance(new Object[]{this.c, attributeSet});
            }
            return newInstance;
        } catch (Exception e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Could not instantiate ");
            sb2.append(cls);
            sb2.append(" class ");
            sb2.append(attributeValue);
            throw new InflateException(sb2.toString(), e);
        }
    }

    private void b(XmlPullParser xmlPullParser, AttributeSet attributeSet, Transition transition) {
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if ((next == 3 && xmlPullParser.getDepth() <= depth) || next == 1) {
                return;
            }
            if (next == 2) {
                if (xmlPullParser.getName().equals("target")) {
                    TypedArray obtainStyledAttributes = this.c.obtainStyledAttributes(attributeSet, Styleable.a);
                    int namedResourceId = TypedArrayUtils.getNamedResourceId(obtainStyledAttributes, xmlPullParser, "targetId", 1, 0);
                    if (namedResourceId != 0) {
                        transition.addTarget(namedResourceId);
                    } else {
                        int namedResourceId2 = TypedArrayUtils.getNamedResourceId(obtainStyledAttributes, xmlPullParser, "excludeId", 2, 0);
                        if (namedResourceId2 != 0) {
                            transition.excludeTarget(namedResourceId2, true);
                        } else {
                            String namedString = TypedArrayUtils.getNamedString(obtainStyledAttributes, xmlPullParser, "targetName", 4);
                            if (namedString != null) {
                                transition.addTarget(namedString);
                            } else {
                                String namedString2 = TypedArrayUtils.getNamedString(obtainStyledAttributes, xmlPullParser, "excludeName", 5);
                                if (namedString2 != null) {
                                    transition.excludeTarget(namedString2, true);
                                } else {
                                    String namedString3 = TypedArrayUtils.getNamedString(obtainStyledAttributes, xmlPullParser, "excludeClass", 3);
                                    if (namedString3 != null) {
                                        try {
                                            transition.excludeTarget(Class.forName(namedString3), true);
                                        } catch (ClassNotFoundException e) {
                                            e = e;
                                            obtainStyledAttributes.recycle();
                                            StringBuilder sb = new StringBuilder();
                                            sb.append("Could not create ");
                                            sb.append(namedString3);
                                            throw new RuntimeException(sb.toString(), e);
                                        }
                                    } else {
                                        String namedString4 = TypedArrayUtils.getNamedString(obtainStyledAttributes, xmlPullParser, "targetClass", 0);
                                        if (namedString4 != null) {
                                            try {
                                                transition.addTarget(Class.forName(namedString4));
                                            } catch (ClassNotFoundException e2) {
                                                e = e2;
                                                namedString3 = namedString4;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    obtainStyledAttributes.recycle();
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Unknown scene name: ");
                    sb2.append(xmlPullParser.getName());
                    throw new RuntimeException(sb2.toString());
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0054, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.support.transition.TransitionManager a(org.xmlpull.v1.XmlPullParser r5, android.util.AttributeSet r6, android.view.ViewGroup r7) {
        /*
            r4 = this;
            int r0 = r5.getDepth()
            r1 = 0
        L_0x0005:
            int r2 = r5.next()
            r3 = 3
            if (r2 != r3) goto L_0x0012
            int r3 = r5.getDepth()
            if (r3 <= r0) goto L_0x0054
        L_0x0012:
            r3 = 1
            if (r2 == r3) goto L_0x0054
            r3 = 2
            if (r2 == r3) goto L_0x0019
            goto L_0x0005
        L_0x0019:
            java.lang.String r2 = r5.getName()
            java.lang.String r3 = "transitionManager"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x002b
            android.support.transition.TransitionManager r1 = new android.support.transition.TransitionManager
            r1.<init>()
            goto L_0x0005
        L_0x002b:
            java.lang.String r3 = "transition"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0039
            if (r1 == 0) goto L_0x0039
            r4.a(r6, r5, r7, r1)
            goto L_0x0005
        L_0x0039:
            java.lang.RuntimeException r6 = new java.lang.RuntimeException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r0 = "Unknown scene name: "
            r7.append(r0)
            java.lang.String r5 = r5.getName()
            r7.append(r5)
            java.lang.String r5 = r7.toString()
            r6.<init>(r5)
            throw r6
        L_0x0054:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.transition.TransitionInflater.a(org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.view.ViewGroup):android.support.transition.TransitionManager");
    }

    private void a(AttributeSet attributeSet, XmlPullParser xmlPullParser, ViewGroup viewGroup, TransitionManager transitionManager) {
        Scene scene;
        TypedArray obtainStyledAttributes = this.c.obtainStyledAttributes(attributeSet, Styleable.b);
        int namedResourceId = TypedArrayUtils.getNamedResourceId(obtainStyledAttributes, xmlPullParser, "transition", 2, -1);
        int namedResourceId2 = TypedArrayUtils.getNamedResourceId(obtainStyledAttributes, xmlPullParser, "fromScene", 0, -1);
        Scene scene2 = null;
        if (namedResourceId2 < 0) {
            scene = null;
        } else {
            scene = Scene.getSceneForLayout(viewGroup, namedResourceId2, this.c);
        }
        int namedResourceId3 = TypedArrayUtils.getNamedResourceId(obtainStyledAttributes, xmlPullParser, "toScene", 1, -1);
        if (namedResourceId3 >= 0) {
            scene2 = Scene.getSceneForLayout(viewGroup, namedResourceId3, this.c);
        }
        if (namedResourceId >= 0) {
            Transition inflateTransition = inflateTransition(namedResourceId);
            if (inflateTransition != null) {
                if (scene2 == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("No toScene for transition ID ");
                    sb.append(namedResourceId);
                    throw new RuntimeException(sb.toString());
                } else if (scene == null) {
                    transitionManager.setTransition(scene2, inflateTransition);
                } else {
                    transitionManager.setTransition(scene, scene2, inflateTransition);
                }
            }
        }
        obtainStyledAttributes.recycle();
    }
}
