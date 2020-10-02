class HandlerTree {
    constructor() {
        this.first = null;
        this.size = 0;
    }

    findHandler(handlerId) {
        let node = this.first
        let response = null

        const internal = (node) => {
            if (node === null) {
                return;
            }
            if (node.id === handlerId) {
                response = node;
                return;
            }

            node.children.forEach(element => {
                internal(element)
            });
        };

        internal(node);
        return response;
    }
}

class Handler {
    constructor(id, children) {
        this.id = id;
        this.children = children;
    }
}