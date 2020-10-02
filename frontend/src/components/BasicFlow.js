
import React, { useEffect, useState, Controls } from 'react';
import api from '../utils/api';
import ReactFlow, { removeElements, addEdge, Background } from 'react-flow-renderer';

const onLoad = (reactFlowInstance) => reactFlowInstance.fitView();

const BasicFlow = () => {
  
  const [elements, setElements] = useState([]);
  const [handlers, setHandlers] = useState([]);
  const [graphRep, setGraphRep] = useState(null);

  const onElementsRemove = (elementsToRemove) => setElements((els) => removeElements(elementsToRemove, els));
  const onConnect = (params) => {
    setElements((els) => addEdge(params, els))
  };

  const createNewNode = (name) => {
    let id = Math.floor(Math.random() * 10000).toString();
    let newElements = elements.concat(
      { id: id, data: { label: <div>{name} #{id}</div> }, position: { x: 100, y: 100 } }
    )
    setElements(newElements);    
  }

  useEffect(() => {
    if (handlers.length === 0) {
      api.get('handlers')
        .then(response => setHandlers(response.data));
    }
  }, [handlers])


  const buildHandlerButton = (id, name) => {
    return (
      <button 
        key={'handler-button-' + id}
        onClick={() => {createNewNode(name)}}
        className='py-2 mt-2 mx-2 bg-green-500 rounded text-white font-bold'>
        {name}
      </button>
    )
  }

  const buildHandlerButtons = () => {
    return(
      handlers.map(handler => buildHandlerButton(handler.id, handler.name))
    )
  }

  return (
    <div className='flex h-full'>
      <div className='flex flex-col px-2 bg-gray-200 w-1/6'>
        <h2 className='text-xl font-bold '>Handlers</h2>
        {buildHandlerButtons()}
        <button className='mt-32 rounded mx-2 py-2 bg-red-500 text-white font-bold'>
          Create
        </button>
      </div>
      <ReactFlow
        onLoad={onLoad}
        onConnect={onConnect}
        onElementsRemove={onElementsRemove}
        elements={elements} 
      >
        <Background
          variant="dots"
          gap={12}
          size={.5}
          />
      </ReactFlow>

    </div>
  );
};

export default BasicFlow;