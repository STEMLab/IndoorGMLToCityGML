<navi:Route 
    xmlns:gml="http://www.opengis.net/gml/3.2" 
    xmlns:xlink="http://www.w3.org/1999/xlink" xmlns="http://www.opengis.net/indoorgml/1.0/core" 
    xmlns:navi="http://www.opengis.net/indoorgml/1.0/navigation"
    xmlns:core="http://www.opengis.net/indoorgml/1.0/core" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    gml:id="IFs" 
    xsi:schemaLocation="http://www.opengis.net/indoorgml/1.0/core http://schemas.opengis.net/indoorgml/1.0/indoorgmlcore.xsd http://www.opengis.net/indoorgml/1.0/navigation http://schemas.opengis.net/indoorgml/1.0/indoorgmlnavi.xsd">
    <navi:startRouteNode>
        <navi:RouteNode gml:id="RNs">
            <navi:referencedState xlink:href="#R6"/>
            <navi:geometry xlink:href="#P6"/>
        </navi:RouteNode>
    </navi:startRouteNode>
    <navi:endRouteNode>
        <navi:RouteNode gml:id="Rne">
            <navi:referencedState xlink:href="#R49"/>
            <navi:geometry xlink:href="#P49"/>
        </navi:RouteNode>
    </navi:endRouteNode>
    <navi:routeNodes gml:id="RNS1">
        <navi:nodeMember>
            <navi:RouteNode gml:id="RN1">
                <navi:referencedState>
                    <State gml:id="R6">
                        <gml:name>R6</gml:name>
                        <gml:boundedBy xsi:nil="true"/>
                        <connects xlink:href="#T1"/>
                        <connects xlink:href="#T108"/>
                        <geometry>
                            <gml:Point gml:id="P6">
                                <gml:pos>-51499.58460674157 -10784.190864197546 3000.0</gml:pos>
                            </gml:Point>
                        </geometry>
                    </State>
                </navi:referencedState>
                <navi:geometry xlink:href="#P6"/>
            </navi:RouteNode>
        </navi:nodeMember>
        <navi:nodeMember>
            <navi:RouteNode gml:id="RN2">
                <navi:referencedState>
                    <State gml:id="R49">
                        <gml:name>R49</gml:name>
                        <gml:boundedBy xsi:nil="true"/>
                        <connects xlink:href="#T1"/>
                        <connects xlink:href="#T2"/>
                        <connects xlink:href="#T3"/>
                        <connects xlink:href="#T10"/>
                        <geometry>
                            <gml:Point gml:id="P49">
                                <gml:pos>-48262.72456179776 -7822.2032098765485 3000.0</gml:pos>
                            </gml:Point>
                        </geometry>
                    </State>
                </navi:referencedState>
                <navi:geometry xlink:href="#P49"/>
            </navi:RouteNode>
        </navi:nodeMember>
    </navi:routeNodes>
    <navi:path gml:id="P1">
        <navi:routeMember>
            <navi:RouteSegment gml:id="RS1">
                <navi:weight>
                    1
                </navi:weight>
                <navi:connects xlink:href="#RN1"/>
                <navi:connects xlink:href="#RN2"/>
                <navi:referencedTransition>
                    <Transition gml:id="T1">
                        <gml:name>T1</gml:name>
                        <gml:boundedBy xsi:nil="true"/>
                        <connects xlink:href="#R6"/>
                        <connects xlink:href="#R49"/>
                        <geometry>
                            <gml:LineString gml:id="LS1">
                                <gml:posList>-51499.58460674157 -10784.190864197546 3000.0 -48262.72456179776 -7822.2032098765485 3000.0</gml:posList>
                            </gml:LineString>
                        </geometry>
                    </Transition>
                </navi:referencedTransition>
                <navi:geometry xlink:href="#LS1"/>
            </navi:RouteSegment>
        </navi:routeMember>
    </navi:path>
</navi:Route> 

