package com.rccsilva.flowmanager.domain.shared.interfaces

interface INode<T>{
    val topic: T
    val handlerId: Int
    val children: List<INode<T>>
}