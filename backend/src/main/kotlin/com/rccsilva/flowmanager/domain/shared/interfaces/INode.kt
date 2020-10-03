package com.rccsilva.flowmanager.domain.shared.interfaces

interface INode<T>{
    val value: T
    val children: List<INode<T>>
}