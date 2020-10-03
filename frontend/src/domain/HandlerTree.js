class FlowRequest {
    constructor(name, handlers) {
        this.name = name;
        this.handlers = handlers;
    }
}

class HandlerNode {
    constructor(id, children) {
        this.id = id;
        this.children = children;
    }

    addChild(node) {
        this.children = this.children.concat(node);
    }
}

export { FlowRequest, HandlerNode }