
import React, { useEffect, useState } from 'react';
import ReactFlow, { removeElements, addEdge, Background } from 'react-flow-renderer';
import api from '../utils/api';
import { FlowRequest, HandlerNode } from '../domain/HandlerTree';

const onLoad = (reactFlowInstance) => reactFlowInstance.fitView();

const BasicFlow = () => {
  
  const [elements, setElements] = useState([]);
  const [handlers, setHandlers] = useState([]);
  const [node, setNode] = useState(null);

  const onElementsRemove = (elementsToRemove) => setElements((els) => removeElements(elementsToRemove, els));
  const onConnect = (params) => {

    let source = elements.find(element => element.id === params.source);
    let target = elements.find(element => element.id === params.target);

    source.nodeRef.addChild(target.nodeRef);

    if (node === null) {
      setNode(source.nodeRef);
    } else if (target === node) {
      setNode(source.nodeRef);
    }

    setElements((els) => addEdge(params, els));
  };

  const createNewNode = (handlerId, name) => {
    let id = Math.floor(Math.random() * 10000).toString();

    const handlerNode = new HandlerNode(handlerId, []);

    let newElements = elements.concat(
      { id: id, 
        data: { label: <div>{name}</div> }, 
        nodeRef: handlerNode,
        position: { x: 100, y: 100 } }
    )
    setElements(newElements);    
  }

  useEffect(() => {
    if (handlers.length === 0) {
      api
        .get('handlers')
        .then(response => setHandlers(response.data));
    }
  }, [handlers])

  const buildHandlerButton = (id, name) => {
    return (
      <button 
        key={'handler-button-' + id}
        onClick={() => {createNewNode(id, name)}}
        className='py-2 mt-2 mx-2 bg-green-500 rounded text-white font-bold'>
        {name}
      </button>
    )
  }

  const buildHandlerButtons = () => {
    return(
      handlers.map(handler => buildHandlerButton(handler.id, handler.name))
    );
  }

  const submitCreateFlowRequest = (event) => {
    event.preventDefault();
    const flowRequest = new FlowRequest(event.target.name.value, node);
    api
      .post('flow', flowRequest)
      .then(response => console.log(response))
      .catch(error => console.log(error))
  }

  return (
    <div className='flex h-full'>
      <div className='flex flex-col px-2 bg-gray-200 w-1/6'>
        <h2 className='text-xl font-bold '>Handlers</h2>
        {buildHandlerButtons()}

        <form onSubmit={submitCreateFlowRequest} className='mt-8'>
          <label htmlFor='name'>
            Name:
          </label>
          <input
            id="name"
            className='rounded border-2 border-gray-500' 
            type='text' />
          <input 
            className='bg-red-500 w-full rounded px-2 py-2 text-white font-bold mt-4 cursor-pointer'
            type='submit'
            value='Create'/>
        </form>
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