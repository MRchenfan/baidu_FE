/*
*  date:
*  author:
*  note:
*    1.和require一样的？
*    2.
*    3.
*    4.
*/
var define, require, esl;
! function(n) {
    function e(n) {
        p(n, L) || (_[n] = 1)
    }

    function t(n, e) {
        function t(n) {
            0 === n.indexOf(".") && i.push(n)
        }
        var i = [];
        if ("string" == typeof n ? t(n) : j(n, function(n) {
            t(n)
        }), i.length > 0) throw new Error("[REQUIRE_FATAL]Relative ID is not allowed in global require: " + i.join(", "));
        var o = P.waitSeconds;
        return o && n instanceof Array && ($ && clearTimeout($), $ = setTimeout(r, 1e3 * o)), H(n, e)
    }

    function r() {
        function n(a, u) {
            if (!o[a] && !p(a, L)) {
                o[a] = 1;
                var f = F[a];
                f ? (u || !p(a, z) || f.hang) && (r[a] || (r[a] = 1, e.push(a)), j(f.depMs, function(e) {
                    n(e.absId, e.hard)
                })) : i[a] || (i[a] = 1, t.push(a))
            }
        }
        var e = [],
            t = [],
            r = {},
            i = {},
            o = {};
        for (var a in _) n(a, 1);
        if (e.length || t.length) throw new Error("[MODULE_TIMEOUT]Hang(" + (e.join(", ") || "none") + ") Miss(" + (t.join(", ") || "none") + ")")
    }

    function i(n) {
        j(Q, function(e) {
            u(n, e.deps, e.factory)
        }), Q.length = 0
    }

    function o(n, e, t) {
        if (null == t && (null == e ? (t = n, n = null) : (t = e, e = null, n instanceof Array && (e = n, n = null))), null != t) {
            var r = window.opera;
            if (!n && document.attachEvent && (!r || "[object Opera]" !== r.toString())) {
                var i = R();
                n = i && i.getAttribute("data-require-id")
            }
            n ? u(n, e, t) : Q[0] = {
                deps: e,
                factory: t
            }
        }
    }

    function a() {
        var n = P.config[this.id];
        return n && "object" == typeof n ? n : {}
    }

    function u(n, e, t) {
        F[n] || (F[n] = {
            id: n,
            depsDec: e,
            deps: e || ["require", "exports", "module"],
            factoryDeps: [],
            factory: t,
            exports: {},
            config: a,
            state: B,
            require: A(n),
            depMs: [],
            depMkv: {},
            depRs: [],
            hang: 0
        })
    }

    function f(n) {
        var e = F[n];
        if (e && !p(n, N)) {
            var t = e.deps,
                r = e.factory,
                i = 0;
            "function" == typeof r && (i = Math.min(r.length, t.length), !e.depsDec && r.toString().replace(/(\/\*([\s\S]*?)\*\/|([^:]|^)\/\/(.*)$)/gm, "").replace(/require\(\s*(['"])([^'"]+)\1\s*\)/g, function(n, e, r) {
                t.push(r)
            }));
            var o = [],
                a = [];
            j(t, function(t, r) {
                var u, f, c = S(t),
                    s = q(c.mod, n);
                s && !C[s] ? (c.res && (f = {
                    id: t,
                    mod: s,
                    res: c.res
                }, a.push(t), e.depRs.push(f)), u = e.depMkv[s], u || (u = {
                    id: c.mod,
                    absId: s,
                    hard: i > r
                }, e.depMs.push(u), e.depMkv[s] = u, o.push(s))) : u = {
                    absId: s
                }, i > r && e.factoryDeps.push(f || u)
            }), e.state = N, l(n), m(o), a.length && e.require(a, function() {
                j(e.depRs, function(e) {
                    e.absId || (e.absId = q(e.id, n))
                }), c()
            })
        }
    }

    function c() {
        for (var n in _) f(n), s(n), d(n)
    }

    function s(n) {
        function e(n) {
            if (f(n), !p(n, N)) return !1;
            if (p(n, z) || t[n]) return !0;
            t[n] = 1;
            var r = F[n],
                i = !0;
            return j(r.depMs, function(n) {
                i = e(n.absId) && i
            }), i && j(r.depRs, function(n) {
                return i = !!n.absId
            }), i && !p(n, z) && (r.state = z), t[n] = 0, i
        }
        var t = {};
        e(n)
    }

    function l(e) {
        function t() {
            if (!r && i.state === z) {
                r = 1;
                var t = 1;
                if (j(i.factoryDeps, function(n) {
                    var e = n.absId;
                    return C[e] ? void 0 : (d(e), t = p(e, L))
                }), t) {
                    try {
                        var o = i.factory,
                            a = "function" == typeof o ? o.apply(n, v(i.factoryDeps, {
                                require: i.require,
                                exports: i.exports,
                                module: i
                            })) : o;
                        null != a && (i.exports = a), i.invokeFactory = null
                    } catch (u) {
                        throw i.hang = 1, u
                    }
                    g(e)
                }
            }
        }
        var r, i = F[e];
        i.invokeFactory = t
    }

    function p(n, e) {
        return F[n] && F[n].state >= e
    }

    function d(n) {
        var e = F[n];
        e && e.invokeFactory && e.invokeFactory()
    }

    function v(n, e) {
        var t = [];
        return j(n, function(n, r) {
            "object" == typeof n && (n = n.absId), t[r] = e[n] || F[n].exports
        }), t
    }

    function h(n, e) {
        if (p(n, L)) return void e();
        var t = G[n];
        t || (t = G[n] = []), t.push(e)
    }

    function g(n) {
        var e = F[n];
        e.state = L, delete _[n];
        for (var t = G[n] || [], r = t.length; r--;) t[r]();
        t.length = 0, G[n] = null
    }

    function m(e, t, r) {
        function i() {
            if ("function" == typeof t && !o) {
                var r = 1;
                j(e, function(n) {
                    return C[n] ? void 0 : r = !!p(n, L)
                }), r && (o = 1, t.apply(n, v(e, C)))
            }
        }
        var o = 0;
        j(e, function(n) {
            C[n] || p(n, L) || (h(n, i), (n.indexOf("!") > 0 ? b : y)(n, r))
        }), i()
    }

    function y(e) {
        function t() {
            var n = X[e];
            D(n || e, r)
        }

        function r() {
            if (a) {
                var t;
                "function" == typeof a.init && (t = a.init.apply(n, v(u, C))), null == t && a.exports && (t = n, j(a.exports.split("."), function(n) {
                    return t = t[n], !!t
                })), o(e, u, function() {
                    return t || {}
                })
            } else i(e);
            c()
        }
        if (!J[e] && !F[e]) {
            J[e] = 1;
            var a = P.shim[e];
            a instanceof Array && (P.shim[e] = a = {
                deps: a
            });
            var u = a && (a.deps || []);
            u ? (j(u, function(n) {
                P.shim[n] || (P.shim[n] = {})
            }), H(u, t)) : t()
        }
    }

    function b(n, e) {
        function t(e) {
            f.exports = e || !0, g(n)
        }

        function r(r) {
            var i = e ? F[e].require : H;
            r.load(u.res, i, t, a.call({
                id: n
            }))
        }
        if (!F[n]) {
            var o = X[n];
            if (o) return void y(o);
            var u = S(n),
                f = {
                    id: n,
                    state: N
                };
            F[n] = f, t.fromText = function(n, e) {
                new Function(e)(), i(n)
            }, r(H(u.mod))
        }
    }

    function x(n, e) {
        var t = T(n, 1, e);
        return t.sort(O), t
    }

    function k() {
        function n(n) {
            X[I(n)] = t
        }
        P.baseUrl = P.baseUrl.replace(/\/$/, "") + "/", K = x(P.paths), W = x(P.map, 1), j(W, function(n) {
            n.v = x(n.v)
        });
        var e = W[W.length - 1];
        e && "*" === e.k && j(W, function(n) {
            n != e && (n.v = n.v.concat(e.v))
        }), V = [], j(P.packages, function(n) {
            var e = n;
            "string" == typeof n && (e = {
                name: n.split("/")[0],
                location: n,
                main: "main"
            }), e.location = e.location || e.name, e.main = (e.main || "main").replace(/\.js$/i, ""), e.reg = U(e.name), V.push(e)
        }), V.sort(O), Y = x(P.urlArgs, 1), X = {};
        for (var t in P.bundles) j(P.bundles[t], n)
    }

    function E(n, e, t) {
        j(e, function(e) {
            return e.reg.test(n) ? (t(e.v, e.k, e), !1) : void 0
        })
    }

    function w(n, e) {
        var t = /(\.[a-z0-9]+)$/i,
            r = /(\?[^#]*)$/,
            i = "",
            o = n,
            a = "";
        r.test(n) && (a = RegExp.$1, n = n.replace(r, "")), t.test(n) && (i = RegExp.$1, o = n.replace(t, "")), null != e && (o = q(o, e));
        var u, f = o;
        return E(o, K, function(n, e) {
            f = f.replace(e, n), u = 1
        }), u || E(o, V, function(n, e, t) {
            f = f.replace(t.name, t.location)
        }), /^([a-z]{2,10}:\/)?\//i.test(f) || (f = P.baseUrl + f), f += i + a, E(o, Y, function(n) {
            f += (f.indexOf("?") > 0 ? "&" : "?") + n
        }), f
    }

    function A(n) {
        function t(t, i) {
            if ("string" == typeof t) {
                if (!r[t]) {
                    var o = q(t, n);
                    if (d(o), !p(o, L)) throw new Error('[MODULE_MISS]"' + o + '" is not exists!');
                    r[t] = F[o].exports
                }
                return r[t]
            }
            if (t instanceof Array) {
                var a = [],
                    u = [];
                j(t, function(t, r) {
                    var i = S(t),
                        o = q(i.mod, n),
                        f = i.res,
                        c = o;
                    if (f) {
                        var s = o + "!" + f;
                        0 !== f.indexOf(".") && X[s] ? o = c = s : c = null
                    }
                    u[r] = c, e(o), a.push(o)
                }), m(a, function() {
                    j(u, function(r, i) {
                        null == r && (r = u[i] = q(t[i], n), e(r))
                    }), m(u, i, n), c()
                }, n), c()
            }
        }
        var r = {};
        return t.toUrl = function(e) {
            return w(e, n || "")
        }, t
    }

    function q(n, e) {
        if (!n) return "";
        e = e || "";
        var t = S(n);
        if (!t) return n;
        var r = t.res,
            i = M(t.mod, e);
        if (E(e, W, function(n) {
            E(i, n, function(n, e) {
                i = i.replace(e, n)
            })
        }), i = I(i), r) {
            var o = p(i, L) && H(i);
            r = o && o.normalize ? o.normalize(r, function(n) {
                return q(n, e)
            }) : q(r, e), i += "!" + r
        }
        return i
    }

    function I(n) {
        return j(V, function(e) {
            var t = e.name;
            return t === n ? (n = t + "/" + e.main, !1) : void 0
        }), n
    }

    function M(n, e) {
        if (0 !== n.indexOf(".")) return n;
        for (var t = e.split("/").slice(0, -1).concat(n.split("/")), r = [], i = 0; i < t.length; i++) {
            var o = t[i];
            switch (o) {
                case ".":
                    break;
                case "..":
                    r.length && ".." !== r[r.length - 1] ? r.pop() : r.push(o);
                    break;
                default:
                    o && r.push(o)
            }
        }
        return r.join("/")
    }

    function S(n) {
        var e = n.split("!");
        return e[0] ? {
            mod: e[0],
            res: e[1]
        } : void 0
    }

    function T(n, e, t) {
        var r = [];
        for (var i in n)
            if (n.hasOwnProperty(i)) {
                var o = {
                    k: i,
                    v: n[i]
                };
                r.push(o), e && (o.reg = "*" === i && t ? /^/ : U(i))
            }
        return r
    }

    function U(n) {
        return new RegExp("^" + n + "(/|$)")
    }

    function j(n, e) {
        if (n instanceof Array)
            for (var t = 0, r = n.length; r > t && e(n[t], t) !== !1; t++);
    }

    function O(n, e) {
        var t = n.k || n.name,
            r = e.k || e.name;
        return "*" === r ? -1 : "*" === t ? 1 : r.length - t.length
    }

    function R() {
        if (Z) return Z;
        if (ne && "interactive" === ne.readyState) return ne;
        for (var n = document.getElementsByTagName("script"), e = n.length; e--;) {
            var t = n[e];
            if ("interactive" === t.readyState) return ne = t, t
        }
    }

    function D(n, e) {
        function t() {
            var n = r.readyState;
            ("undefined" == typeof n || /^(loaded|complete)$/.test(n)) && (r.onload = r.onreadystatechange = null, r = null, e())
        }
        var r = document.createElement("script");
        r.setAttribute("data-require-id", n), r.src = w(n + ".js"), r.async = !0, r.readyState ? r.onreadystatechange = t : r.onload = t, Z = r, te ? ee.insertBefore(r, te) : ee.appendChild(r), Z = null
    }
    var $, F = {},
        B = 1,
        N = 2,
        z = 3,
        L = 4,
        _ = {},
        C = {
            require: t,
            exports: 1,
            module: 1
        },
        H = A(),
        P = {
            baseUrl: "./",
            paths: {},
            config: {},
            map: {},
            packages: [],
            shim: {},
            waitSeconds: 0,
            bundles: {},
            urlArgs: {}
        };
    t.version = "2.1.4", t.loader = "esl", t.toUrl = H.toUrl;
    var Q = [];
    o.amd = {};
    var G = {},
        J = {};
    t.config = function(n) {
        if (n) {
            for (var e in P) {
                var t = n[e],
                    r = P[e];
                if (t)
                    if ("urlArgs" === e && "string" == typeof t) P.urlArgs["*"] = t;
                    else if (r instanceof Array) r.push.apply(r, t);
                else if ("object" == typeof r)
                    for (var i in t) r[i] = t[i];
                else P[e] = t
            }
            k()
        }
    }, k();
    var K, V, W, X, Y, Z, ne, ee = document.getElementsByTagName("head")[0],
        te = document.getElementsByTagName("base")[0];
    te && (ee = te.parentNode), define || (define = o, require || (require = t), esl = t);
    var re;
    ! function() {
        for (var n = document.getElementsByTagName("script"), e = n.length; e--;) {
            var t = n[e];
            if (re = t.getAttribute("data-main")) break
        }
    }(), re && setTimeout(function() {
        t([re])
    }, 4)
}(this);