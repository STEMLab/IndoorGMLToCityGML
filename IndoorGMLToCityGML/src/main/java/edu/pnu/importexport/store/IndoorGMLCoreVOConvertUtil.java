package edu.pnu.importexport.store;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.opengis.indoorgml.v_1_0.vo.core.CellSpace;
import net.opengis.indoorgml.v_1_0.vo.core.CellSpaceBoundary;
import net.opengis.indoorgml.v_1_0.vo.core.Edges;
import net.opengis.indoorgml.v_1_0.vo.core.ExternalReference;
import net.opengis.indoorgml.v_1_0.vo.core.IndoorFeatures;
import net.opengis.indoorgml.v_1_0.vo.core.IndoorObject;
import net.opengis.indoorgml.v_1_0.vo.core.InterEdges;
import net.opengis.indoorgml.v_1_0.vo.core.InterLayerConnection;
import net.opengis.indoorgml.v_1_0.vo.core.MultiLayeredGraph;
import net.opengis.indoorgml.v_1_0.vo.core.Nodes;
import net.opengis.indoorgml.v_1_0.vo.core.PrimalSpaceFeatures;
import net.opengis.indoorgml.v_1_0.vo.core.SpaceLayer;
import net.opengis.indoorgml.v_1_0.vo.core.SpaceLayers;
import net.opengis.indoorgml.v_1_0.vo.core.State;
import net.opengis.indoorgml.v_1_0.vo.core.Transition;
import net.opengis.indoorgml.v_1_0.vo.spatial.VOCurve;
import net.opengis.indoorgml.v_1_0.vo.spatial.VOPoint;
import net.opengis.indoorgml.v_1_0.vo.spatial.VOSolid;
import net.opengis.indoorgml.v_1_0.vo.spatial.VOSurface;
import edu.pnu.importexport.BindingNode;

public class IndoorGMLCoreVOConvertUtil {
	
	public static IndoorObject setIndoorObject(IndoorObject io, BindingNode node, String IndoorObjectType, Map<String, Object> idRegistryMap){
		/* attributes */
		io.setGmlID((String)node.getAttribute("GMLID") );
		io.setName((String) node.getAttribute("NAME/VALUE"));
		io.setNameCodeSpace((String) node.getAttribute("NAME/CODESPACE"));
		io.setDescription((String) node.getAttribute("DESCRIPTION/VALUE"));
		io.setIndoorObjectType(IndoorObjectType);
		
		if (!idRegistryMap.containsKey(io.getGmlID())) {
			idRegistryMap.put(io.getGmlID(), io);
		}
		return io;
	}
	
	public static IndoorFeatures createIndoorFeature(BindingNode node, Map<String, Object> idRegistryMap) {
		/* SuperClass Init */
		IndoorFeatures indoorFeatures = new IndoorFeatures();
		setIndoorObject(indoorFeatures, node, "INDOORFEATURE", idRegistryMap);
		indoorFeatures.indoorObject.add(indoorFeatures);
		/* associations */
		BindingNode primalSpaceFeatureNode = node.getAssociation("PRIMALSPACEFEATURE");
		if(primalSpaceFeatureNode != null){
			PrimalSpaceFeatures primalSpaceFeatures = createPrimalSpaceFeature(primalSpaceFeatureNode, idRegistryMap);
			primalSpaceFeatures.parents = indoorFeatures;
			
			indoorFeatures.setPrimalSpaceFeatures(primalSpaceFeatures);
			for(IndoorObject io : primalSpaceFeatures.indoorObject){
				indoorFeatures.indoorObject.add(io);	
			}
		}
		BindingNode multiLayeredGraphNode = node.getAssociation("MULTILAYEREDGRAPH");
		if(multiLayeredGraphNode != null){
			MultiLayeredGraph multiLayeredGraph = createMultiLayeredGraph(multiLayeredGraphNode, idRegistryMap);
			multiLayeredGraph.parents = indoorFeatures;
			
			indoorFeatures.setMultiLayeredGraph(multiLayeredGraph);
			for(IndoorObject io : multiLayeredGraph.indoorObject){
				indoorFeatures.indoorObject.add(io);	
			}
		}
		
		indoorFeatures = setXLinkObject(indoorFeatures, idRegistryMap);
		return indoorFeatures;
	}

	private static PrimalSpaceFeatures createPrimalSpaceFeature(BindingNode node, Map<String, Object> idRegistryMap) {
		/* SuperClass Init */
		PrimalSpaceFeatures primalSpaceFeatures = new PrimalSpaceFeatures();
		setIndoorObject(primalSpaceFeatures, node, "PRIMALSPACEFEATURES", idRegistryMap);
		primalSpaceFeatures.indoorObject.add(primalSpaceFeatures);
		/* collections */
		ArrayList<CellSpace> cellSpaceMember = new ArrayList<CellSpace>();
		List<BindingNode> cellSpaceNode = node.getCollection("CELLSPCACE");
		for(BindingNode csNode : cellSpaceNode){
			CellSpace cellSpace = createCellSpace(csNode, idRegistryMap);
			cellSpace.parents = primalSpaceFeatures;
			
			cellSpaceMember.add(cellSpace);
			for(IndoorObject io : cellSpace.indoorObject){
				primalSpaceFeatures.indoorObject.add(io);	
			}
		}
		primalSpaceFeatures.setCellSpace(cellSpaceMember);
		
		ArrayList<CellSpaceBoundary> cellSpaceBoundaryMember = new ArrayList<CellSpaceBoundary>();
		List<BindingNode> cellSpaceBoundaryNode = node.getCollection("CELLSPCACEBOUNDARY");
		for(BindingNode csbNode : cellSpaceBoundaryNode){
			CellSpaceBoundary cellSpaceBoundary = createCellSpaceBoundary(csbNode, idRegistryMap);
			cellSpaceBoundary.parents = primalSpaceFeatures;
			
			cellSpaceBoundaryMember.add(cellSpaceBoundary);
			for(IndoorObject io : cellSpaceBoundary.indoorObject){
				primalSpaceFeatures.indoorObject.add(io);	
			}
		}
		primalSpaceFeatures.setCellSpaceBoundary(cellSpaceBoundaryMember);
		
		return primalSpaceFeatures;
	}

	@SuppressWarnings("unchecked")
	private static CellSpace createCellSpace(BindingNode node, Map<String, Object> idRegistryMap) {
		/* SuperClass Init */
		CellSpace cellSpace = new CellSpace();
		setIndoorObject(cellSpace, node, "CELLSPACE", idRegistryMap);
		cellSpace.indoorObject.add(cellSpace);
		/* navigation */
		cellSpace.setNavigableType((String) node.getAttribute("NAVIGABLETYPE"));
		if(!cellSpace.getNavigableType().equals("CellSpaceType")){
			cellSpace.setClazz((String) node.getAttribute("CLAZZ/VALUE"));
			cellSpace.setClassCodeSpace((String) node.getAttribute("CLAZZ/CODESPACE"));
			cellSpace.setFunction((String) node.getAttribute("FUNCTION/VALUE"));
			cellSpace.setFunctionCodeSpace((String) node.getAttribute("FUNCTION/CODESPACE"));
			cellSpace.setUsage((String) node.getAttribute("USAGE/VALUE"));
			cellSpace.setUsageCodeSpace((String) node.getAttribute("USAGE/CODESPACE"));
		}
		/* attributes */
		cellSpace.setHrefDuality((String) node.getAttribute("DUALITY"));
		cellSpace.setHrefPartialBoundedBy((ArrayList<String>) node.getAttribute("PARTIALBOUNDEDBY"));
		
		/* geometry associations */
		BindingNode geometry2DNode = node.getAssociation("GEOMETRY2D");
		if(geometry2DNode != null){
			VOSurface geometry2D = GMLVOConvertUtil.createSurface(geometry2DNode);
			cellSpace.setGeometry2D(geometry2D);
		}
		BindingNode geometry3DNode = node.getAssociation("GEOMETRY3D");
		if(geometry3DNode != null){
			VOSolid geometry3D = GMLVOConvertUtil.createSolid(geometry3DNode);
			cellSpace.setGeometry3D(geometry3D);

			//System.out.println("Volume of Space : " + geometry3D.getGeometry().getVolume());
		}
		
		/* collections */
		ArrayList<ExternalReference> externalReferenceList = new ArrayList<ExternalReference>(); 
		List<BindingNode> externalReferenceNode = node.getCollection("EXTERNALREFERENCE");
		for(BindingNode erNode : externalReferenceNode){
			ExternalReference externalReference = createExternalReference(erNode, idRegistryMap);
			externalReference.parents = cellSpace;
			externalReferenceList.add(externalReference);
		}
		cellSpace.setExternalReference(externalReferenceList);
		
		return cellSpace;
	}

	private static CellSpaceBoundary createCellSpaceBoundary(BindingNode node, Map<String, Object> idRegistryMap) {
		/* SuperClass Init */
		CellSpaceBoundary cellSpaceBoundary = new CellSpaceBoundary();
		setIndoorObject(cellSpaceBoundary, node, "CELLSPACEBOUNDARY", idRegistryMap);
		cellSpaceBoundary.indoorObject.add(cellSpaceBoundary);
		/* navigation */
		cellSpaceBoundary.setNavigableType((String) node.getAttribute("NAVIGABLETYPE"));
		/* attributes */		
		cellSpaceBoundary.setHrefDuality((String) node.getAttribute("DUALITY"));
		/* geometry associations */
		BindingNode geometry2DNode = node.getAssociation("GEOMETRY2D");
		if(geometry2DNode != null){
			VOCurve geometry2D = GMLVOConvertUtil.createCurve(geometry2DNode);
			cellSpaceBoundary.setGeometry2D(geometry2D);
		}
		BindingNode geometry3DNode = node.getAssociation("GEOMETRY3D");
		if(geometry3DNode != null){
			VOSurface geometry3D = GMLVOConvertUtil.createSurface(geometry3DNode);
			cellSpaceBoundary.setGeometry3D(geometry3D);
		}
		/* collections */
		ArrayList<ExternalReference> externalReferenceList = new ArrayList<ExternalReference>(); 
		List<BindingNode> externalReferenceNode = node.getCollection("EXTERNALREFERENCE");
		for(BindingNode erNode : externalReferenceNode){
			ExternalReference externalReference = createExternalReference(erNode, idRegistryMap);
			externalReference.parents = cellSpaceBoundary;
			externalReferenceList.add(externalReference);
		}
		cellSpaceBoundary.setExternalReference(externalReferenceList);
		
		return cellSpaceBoundary;
	}
	
	private static ExternalReference createExternalReference(BindingNode node, Map<String, Object> idRegistryMap) {
		ExternalReference externalReference = new ExternalReference();
		externalReference.setInformationSystem((String) node.getAttribute("INFORMATIONSYSTEM"));
		externalReference.setName((String) node.getAttribute("NAME"));
		externalReference.setUri((String) node.getAttribute("URI"));
		return externalReference;
	}

	private static MultiLayeredGraph createMultiLayeredGraph(BindingNode node, Map<String, Object> idRegistryMap) {
		/* SuperClass Init */
		MultiLayeredGraph multiLayeredGraph = new MultiLayeredGraph();
		setIndoorObject(multiLayeredGraph, node, "MULTILAYEREDGRAPH", idRegistryMap);
		multiLayeredGraph.indoorObject.add(multiLayeredGraph);
		/* collections */
		ArrayList<InterEdges> interEdgesMember = new ArrayList<InterEdges>();
		List<BindingNode> interEdgesNode = node.getCollection("INTEREDGES");
		for(BindingNode ieNode : interEdgesNode){
			InterEdges interEdges = createInterEdges(ieNode, idRegistryMap);
			interEdges.parents = multiLayeredGraph;
			
			interEdgesMember.add(interEdges);
			for(IndoorObject io : interEdges.indoorObject){
				multiLayeredGraph.indoorObject.add(io);	
			}
		}
		multiLayeredGraph.setInterEdges(interEdgesMember);
		
		ArrayList<SpaceLayers> spaceLayersMember = new ArrayList<SpaceLayers>();
		List<BindingNode> spaceLayersNode = node.getCollection("SPACELAYERS");
		for(BindingNode slsNode : spaceLayersNode){
			SpaceLayers spaceLayers = createSpaceLayers(slsNode, idRegistryMap);
			spaceLayers.parents = multiLayeredGraph;
			
			spaceLayersMember.add(spaceLayers);
			for(IndoorObject io : spaceLayers.indoorObject){
				multiLayeredGraph.indoorObject.add(io);	
			}
		}
		multiLayeredGraph.setSpaceLayers(spaceLayersMember);
		
		return multiLayeredGraph;
	}

	private static InterEdges createInterEdges(BindingNode node, Map<String, Object> idRegistryMap) {
		/* SuperClass Init */
		InterEdges interEdges = new InterEdges();
		setIndoorObject(interEdges, node, "INTEREDGES", idRegistryMap);
		interEdges.indoorObject.add(interEdges);
		
		/* collections */
		ArrayList<InterLayerConnection> interLayerConnectionMember = new ArrayList<InterLayerConnection>();
		List<BindingNode> interLayerConnectionNode = node.getCollection("INTERLAYERCONNECTION");
		for(BindingNode ilcNode : interLayerConnectionNode){
			InterLayerConnection interLayerConnection = createInterLayerConnection(ilcNode, idRegistryMap);
			interLayerConnection.parents = interEdges;
			
			interLayerConnectionMember.add(interLayerConnection);
			for(IndoorObject io : interLayerConnection.indoorObject){
				interEdges.indoorObject.add(io);
			}
		}
		interEdges.setInterLayerConnectionMember(interLayerConnectionMember);
		
		return interEdges;
	}
	
	@SuppressWarnings("unchecked")
	private static InterLayerConnection createInterLayerConnection(BindingNode node, Map<String, Object> idRegistryMap) {
		/* SuperClass Init */
		InterLayerConnection interLayerConnection = new InterLayerConnection();
		setIndoorObject(interLayerConnection, node, "INTERLAYERCONNECTION", idRegistryMap);
		interLayerConnection.indoorObject.add(interLayerConnection);
		/* attributes */
		interLayerConnection.setTypeOfTopoExpression((String) node.getAttribute("TYPEOFTOPOEXPRESSION"));
		interLayerConnection.setComment((String) node.getAttribute("COMMENT"));
		interLayerConnection.setHrefConnectedLayers((ArrayList<String>) node.getAttribute("CONNECTEDLAYERS"));
		interLayerConnection.setHrefInterConnects((ArrayList<String>) node.getAttribute("INTERCONNECTS"));
		
		return interLayerConnection;
	}
	
	private static SpaceLayers createSpaceLayers(BindingNode node, Map<String, Object> idRegistryMap) {
		/* SuperClass Init */
		SpaceLayers spaceLayers = new SpaceLayers();
		setIndoorObject(spaceLayers, node, "SPACELAYERS", idRegistryMap);
		spaceLayers.indoorObject.add(spaceLayers);
		
		/* collections */
		ArrayList<SpaceLayer> spaceLayerMember = new ArrayList<SpaceLayer>();
		List<BindingNode> spaceLayersNode = node.getCollection("SPACELAYER");
		for(BindingNode slNode : spaceLayersNode){
			SpaceLayer spaceLayer = createSpaceLayer(slNode, idRegistryMap);
			spaceLayer.parents = spaceLayers;
			
			spaceLayerMember.add(spaceLayer);
			for(IndoorObject io : spaceLayer.indoorObject){
				spaceLayers.indoorObject.add(io);	
			}
		}
		spaceLayers.setSpaceLayerMember(spaceLayerMember);
		
		return spaceLayers;
	}

	private static SpaceLayer createSpaceLayer(BindingNode node, Map<String, Object> idRegistryMap) {
		/* SuperClass Init */
		SpaceLayer spaceLayer = new SpaceLayer();
		setIndoorObject(spaceLayer, node, "SPACELAYER", idRegistryMap);
		spaceLayer.indoorObject.add(spaceLayer);
		/* attributes */
		spaceLayer.setClazz((String) node.getAttribute("CLAZZ/VALUE"));
		spaceLayer.setClassCodeSpace((String) node.getAttribute("CLAZZ/CODESPACE"));
		spaceLayer.setFunction((String) node.getAttribute("FUNCTION/VALUE"));
		spaceLayer.setFunctionCodeSpace((String) node.getAttribute("FUNCTION/CODESPACE"));
		spaceLayer.setUsage((String) node.getAttribute("USAGE/VALUE"));
		spaceLayer.setUsageCodeSpace((String) node.getAttribute("USAGE/CODESPACE"));
		spaceLayer.setTeminationDate((Date) node.getAttribute("TERMINATEDATE"));
		spaceLayer.setCreationDate((Date) node.getAttribute("CREATIONDATE"));
		/* collections */
		ArrayList<Nodes> nodes = new ArrayList<Nodes>();
		List<BindingNode> nodesNode = node.getCollection("NODES");
		for(BindingNode nNode : nodesNode){
			Nodes n = createNodes(nNode, idRegistryMap);
			n.parents = spaceLayer;
			
			nodes.add(n);
			for(IndoorObject io : n.indoorObject){
				spaceLayer.indoorObject.add(io);	
			}
		}
		spaceLayer.setNodes(nodes);
		
		ArrayList<Edges> edges = new ArrayList<Edges>();
		List<BindingNode> edgesNode = node.getCollection("EDGES");
		for(BindingNode eNode : edgesNode){
			Edges e = createEdges(eNode, idRegistryMap);
			e.parents = spaceLayer;
			
			edges.add(e);
			for(IndoorObject io : e.indoorObject){
				spaceLayer.indoorObject.add(io);	
			}
		}
		spaceLayer.setEdges(edges);
		
		return spaceLayer;
	}

	private static Nodes createNodes(BindingNode node, Map<String, Object> idRegistryMap) {
		/* SuperClass Init */
		Nodes nodes = new Nodes();
		setIndoorObject(nodes, node, "NODES", idRegistryMap);
		nodes.indoorObject.add(nodes);
		/* collections */
		ArrayList<State> stateMember = new ArrayList<State>();
		List<BindingNode> stateMemberNode = node.getCollection("STATEMEMBER");
		for(BindingNode smNode : stateMemberNode){
			State state = createState(smNode, idRegistryMap);
			state.parents = nodes;
			
			stateMember.add(state);
			for(IndoorObject io : state.indoorObject){
				nodes.indoorObject.add(io);	
			}
		}
		nodes.setStateMember(stateMember);
		
		return nodes;
	}

	@SuppressWarnings("unchecked")
	public static State createState(BindingNode node, Map<String, Object> idRegistryMap) {
		/* SuperClass Init */
		State state = new State();
		setIndoorObject(state, node, "STATE", idRegistryMap);
		state.indoorObject.add(state);
		/* attributes */
		state.setHrefDuality((String) node.getAttribute("DUALITY"));
		state.setHrefConnects((ArrayList<String>) node.getAttribute("CONNECTS"));
		
		/* geometry associations */
		BindingNode geometryNode = node.getAssociation("GEOMETRY");
		if(geometryNode != null){
			VOPoint geometry = GMLVOConvertUtil.createPoint(geometryNode);
			state.setGeometry(geometry);
		}
		
		return state;
	}

	private static Edges createEdges(BindingNode node, Map<String, Object> idRegistryMap) {
		/* SuperClass Init */
		Edges edges = new Edges();
		setIndoorObject(edges, node, "EDGES", idRegistryMap);
		edges.indoorObject.add(edges);
		/* collections */
		ArrayList<Transition> transitionMember = new ArrayList<Transition>();
		List<BindingNode> transitionMemberNode = node.getCollection("TRANSITIONMEMBER");
		for(BindingNode tmNode : transitionMemberNode){
			Transition transition = createTransition(tmNode, idRegistryMap);
			transition.parents = edges;
			
			transitionMember.add(transition);
			for(IndoorObject io : transition.indoorObject){
				edges.indoorObject.add(io);	
			}
		}
		edges.setTransitionMember(transitionMember);
		
		return edges;
	}

	@SuppressWarnings("unchecked")
	public static Transition createTransition(BindingNode node, Map<String, Object> idRegistryMap) {
		/* SuperClass Init */
		Transition transition = new Transition();
		setIndoorObject(transition, node, "TRANSITION", idRegistryMap);
		transition.indoorObject.add(transition);
		/* attributes */
		transition.setHrefDuality((String) node.getAttribute("DUALITY"));
		transition.setHrefConnects((ArrayList<String>) node.getAttribute("CONNECTS"));
		
		/* geometry associations */
		BindingNode geometryNode = node.getAssociation("GEOMETRY");
		if(geometryNode != null){
			VOCurve geometry = GMLVOConvertUtil.createCurve(geometryNode);
			transition.setGeometry(geometry);
		}
		
		return transition;
	}

	/* Set XLink Objects (Duality, Connects ...)*/
	public static IndoorFeatures setXLinkObject(IndoorFeatures indoorFeatures, Map<String, Object> idRegistryMap) {
		PrimalSpaceFeatures psf = indoorFeatures.getPrimalSpaceFeatures();
		psf = setXLinkObject(psf, idRegistryMap);
		
		MultiLayeredGraph mlg = indoorFeatures.getMultiLayeredGraph();
		mlg = setXLinkObject(mlg, idRegistryMap);
		
		return indoorFeatures;
	}
	
	public static PrimalSpaceFeatures setXLinkObject(PrimalSpaceFeatures psf, Map<String, Object> idRegistryMap) {
		ArrayList<CellSpace> cellSpaceMember = psf.getCellSpace();
		for (CellSpace cellSpace : cellSpaceMember) {
			cellSpace = setXLinkObject(cellSpace, idRegistryMap);			
		}
		
		ArrayList<CellSpaceBoundary> cellSpaceBoundaryMember = psf.getCellSpaceBoundary();
		for (CellSpaceBoundary boundary : cellSpaceBoundaryMember) {
			boundary = setXLinkObject(boundary, idRegistryMap);
		}
		
		return psf;
	}
	
	public static CellSpace setXLinkObject(CellSpace cellSpace, Map<String, Object> idRegistryMap) {
		String hrefDuality = cellSpace.getHrefDuality();
		if (hrefDuality != null) {
			hrefDuality = hrefDuality.replaceAll("#", "");
			if (idRegistryMap.containsKey(hrefDuality)) {
				State duality = (State) idRegistryMap.get(hrefDuality);
				cellSpace.setDuality(duality);
			}
		}
		
		ArrayList<String> hrefPartialBoundedBy = cellSpace.getHrefPartialBoundedBy();
		if (hrefPartialBoundedBy != null && hrefPartialBoundedBy.size() > 0) {
			ArrayList<CellSpaceBoundary> partialBoundedBy = new ArrayList<CellSpaceBoundary>();
			for (String hrefPF : hrefPartialBoundedBy) {
				hrefPF = hrefPF.replaceAll("#", "");
				if (idRegistryMap.containsKey(hrefPF)) {
					CellSpaceBoundary pb = (CellSpaceBoundary) idRegistryMap.get(hrefPF);
					partialBoundedBy.add(pb);
				}
			}
			
			if (partialBoundedBy.size() > 0) {
				cellSpace.setPartialBoundedBy(partialBoundedBy);
			}
		}
		
		return cellSpace;
	}
	
	public static CellSpaceBoundary setXLinkObject(CellSpaceBoundary cellSpaceBoundary, Map<String, Object> idRegistryMap) {
		String hrefDuality = cellSpaceBoundary.getHrefDuality();
		if (hrefDuality != null) {
			hrefDuality = hrefDuality.replaceAll("#", "");
			if (idRegistryMap.containsKey(hrefDuality)) {
				Transition duality = (Transition) idRegistryMap.get(hrefDuality);
				cellSpaceBoundary.setDuality(duality);
			}
		}
		
		return cellSpaceBoundary;
	}
	
	public static MultiLayeredGraph setXLinkObject(MultiLayeredGraph mlg, Map<String, Object> idRegistryMap) {
		ArrayList<SpaceLayers> spaceLayersMember = mlg.getSpaceLayers();
		for (SpaceLayers spaceLayers : spaceLayersMember) {
			ArrayList<SpaceLayer> spaceLayerMember = spaceLayers.getSpaceLayerMember();
			for (SpaceLayer spaceLayer : spaceLayerMember) {
				ArrayList<Nodes> nodesMember = spaceLayer.getNodes();
				for (Nodes nodes : nodesMember) {
					ArrayList<State> stateMember = nodes.getStateMember();
					for (State state : stateMember) {
						state = setXLinkObject(state, idRegistryMap);
					}
				}
				
				ArrayList<Edges> edgesMember = spaceLayer.getEdges();
				for (Edges edges : edgesMember) {
					ArrayList<Transition> transitionMember = edges.getTransitionMember();
					for (Transition transition : transitionMember) {
						transition = setXLinkObject(transition, idRegistryMap);
					}
				}
			}
		}
		
		ArrayList<InterEdges> interEdgesMember = mlg.getInterEdges();
		for (InterEdges interEdges : interEdgesMember) {
			ArrayList<InterLayerConnection> interLayerConnectionMember = interEdges.getInterLayerConnectionMember();
			for (InterLayerConnection ilc : interLayerConnectionMember) {
				ilc = setXLinkObject(ilc, idRegistryMap);
			}
		}
		
		return mlg;
	}
	
	public static State setXLinkObject(State state, Map<String, Object> idRegistryMap) {
		String hrefDuality = state.getHrefDuality();
		if (hrefDuality != null) {
			hrefDuality = hrefDuality.replaceAll("#", "");
			if (idRegistryMap.containsKey(hrefDuality)) {
				CellSpace duality = (CellSpace) idRegistryMap.get(hrefDuality);
				state.setDuality(duality);
			}
		}
		
		ArrayList<String> hrefConnects = state.getHrefConnects();
		if (hrefConnects != null && hrefConnects.size() > 0) {
			ArrayList<Transition> connects = new ArrayList<Transition>();
			for (String hrefConnect : hrefConnects) {
				hrefConnect = hrefConnect.replaceAll("#", "");
				if (idRegistryMap.containsKey(hrefConnect)) {
					Transition transition = (Transition) idRegistryMap.get(hrefConnect);
					connects.add(transition);
				}
			}
			
			if (connects.size() > 0) {
				state.setConnects(connects);
			}
		}
		
		return state;
	}
	
	public static Transition setXLinkObject(Transition transition, Map<String, Object> idRegistryMap) {
		String hrefDuality = transition.getHrefDuality();
		if (hrefDuality != null) {
			hrefDuality = hrefDuality.replaceAll("#", "");
			if (idRegistryMap.containsKey(hrefDuality)) {
				CellSpaceBoundary duality = (CellSpaceBoundary) idRegistryMap.get(hrefDuality);
				transition.setDuality(duality);
			}
		}

		ArrayList<String> hrefConnects = transition.getHrefConnects();
		if (hrefConnects != null && hrefConnects.size() > 0) {
			ArrayList<State> connects = new ArrayList<State>();
			for (String hrefConnect : hrefConnects) {
				hrefConnect = hrefConnect.replaceAll("#", "");
				if (idRegistryMap.containsKey(hrefConnect)) {
					State state = (State) idRegistryMap.get(hrefConnect);
					connects.add(state);
				}
			}
			
			if (connects.size() > 0) {
				transition.setConnects(connects);
			}
		}
		
		return transition;
	}
	
	public static InterLayerConnection setXLinkObject(InterLayerConnection ilc, Map<String, Object> idRegistryMap) {
		ArrayList<String> hrefInterConnects = ilc.getHrefInterConnects();
		if (hrefInterConnects != null && hrefInterConnects.size() > 0) {
			ArrayList<State> interConnects = new ArrayList<State>();
			for (String hrefInterConnect : hrefInterConnects) {
				hrefInterConnect = hrefInterConnect.replaceAll("#", "");
				if (idRegistryMap.containsKey(hrefInterConnect)) {
					State state = (State) idRegistryMap.get(hrefInterConnect);
					interConnects.add(state);
				}
			}
			
			if (interConnects.size() > 0) {
				ilc.setInterConnects(interConnects);
			}
		}
		
		ArrayList<String> hrefConnectedLayers = ilc.getHrefConnectedLayers();
		if (hrefConnectedLayers != null && hrefConnectedLayers.size() > 0) {
			ArrayList<SpaceLayer> connectedLayers = new ArrayList<SpaceLayer>();
			for (String hrefConnectedLayer : hrefConnectedLayers) {
				hrefConnectedLayer = hrefConnectedLayer.replaceAll("#", "");
				if (idRegistryMap.containsKey(hrefConnectedLayer)) {
					SpaceLayer spaceLayer = (SpaceLayer) idRegistryMap.get(hrefConnectedLayer);
					connectedLayers.add(spaceLayer);
				}
			}
			
			if (connectedLayers.size() > 0) {
				ilc.setConnectedLayers(connectedLayers);
			}
		}
		
		return ilc;
	}
	
}
