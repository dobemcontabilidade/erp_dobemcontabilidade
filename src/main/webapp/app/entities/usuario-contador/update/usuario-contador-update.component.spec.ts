import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { IAdministrador } from 'app/entities/administrador/administrador.model';
import { AdministradorService } from 'app/entities/administrador/service/administrador.service';
import { IUsuarioContador } from '../usuario-contador.model';
import { UsuarioContadorService } from '../service/usuario-contador.service';
import { UsuarioContadorFormService } from './usuario-contador-form.service';

import { UsuarioContadorUpdateComponent } from './usuario-contador-update.component';

describe('UsuarioContador Management Update Component', () => {
  let comp: UsuarioContadorUpdateComponent;
  let fixture: ComponentFixture<UsuarioContadorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let usuarioContadorFormService: UsuarioContadorFormService;
  let usuarioContadorService: UsuarioContadorService;
  let contadorService: ContadorService;
  let administradorService: AdministradorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [UsuarioContadorUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(UsuarioContadorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UsuarioContadorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    usuarioContadorFormService = TestBed.inject(UsuarioContadorFormService);
    usuarioContadorService = TestBed.inject(UsuarioContadorService);
    contadorService = TestBed.inject(ContadorService);
    administradorService = TestBed.inject(AdministradorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call contador query and add missing value', () => {
      const usuarioContador: IUsuarioContador = { id: 456 };
      const contador: IContador = { id: 3969 };
      usuarioContador.contador = contador;

      const contadorCollection: IContador[] = [{ id: 22968 }];
      jest.spyOn(contadorService, 'query').mockReturnValue(of(new HttpResponse({ body: contadorCollection })));
      const expectedCollection: IContador[] = [contador, ...contadorCollection];
      jest.spyOn(contadorService, 'addContadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ usuarioContador });
      comp.ngOnInit();

      expect(contadorService.query).toHaveBeenCalled();
      expect(contadorService.addContadorToCollectionIfMissing).toHaveBeenCalledWith(contadorCollection, contador);
      expect(comp.contadorsCollection).toEqual(expectedCollection);
    });

    it('Should call Administrador query and add missing value', () => {
      const usuarioContador: IUsuarioContador = { id: 456 };
      const administrador: IAdministrador = { id: 9822 };
      usuarioContador.administrador = administrador;

      const administradorCollection: IAdministrador[] = [{ id: 14701 }];
      jest.spyOn(administradorService, 'query').mockReturnValue(of(new HttpResponse({ body: administradorCollection })));
      const additionalAdministradors = [administrador];
      const expectedCollection: IAdministrador[] = [...additionalAdministradors, ...administradorCollection];
      jest.spyOn(administradorService, 'addAdministradorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ usuarioContador });
      comp.ngOnInit();

      expect(administradorService.query).toHaveBeenCalled();
      expect(administradorService.addAdministradorToCollectionIfMissing).toHaveBeenCalledWith(
        administradorCollection,
        ...additionalAdministradors.map(expect.objectContaining),
      );
      expect(comp.administradorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const usuarioContador: IUsuarioContador = { id: 456 };
      const contador: IContador = { id: 5629 };
      usuarioContador.contador = contador;
      const administrador: IAdministrador = { id: 26884 };
      usuarioContador.administrador = administrador;

      activatedRoute.data = of({ usuarioContador });
      comp.ngOnInit();

      expect(comp.contadorsCollection).toContain(contador);
      expect(comp.administradorsSharedCollection).toContain(administrador);
      expect(comp.usuarioContador).toEqual(usuarioContador);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUsuarioContador>>();
      const usuarioContador = { id: 123 };
      jest.spyOn(usuarioContadorFormService, 'getUsuarioContador').mockReturnValue(usuarioContador);
      jest.spyOn(usuarioContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usuarioContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: usuarioContador }));
      saveSubject.complete();

      // THEN
      expect(usuarioContadorFormService.getUsuarioContador).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(usuarioContadorService.update).toHaveBeenCalledWith(expect.objectContaining(usuarioContador));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUsuarioContador>>();
      const usuarioContador = { id: 123 };
      jest.spyOn(usuarioContadorFormService, 'getUsuarioContador').mockReturnValue({ id: null });
      jest.spyOn(usuarioContadorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usuarioContador: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: usuarioContador }));
      saveSubject.complete();

      // THEN
      expect(usuarioContadorFormService.getUsuarioContador).toHaveBeenCalled();
      expect(usuarioContadorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUsuarioContador>>();
      const usuarioContador = { id: 123 };
      jest.spyOn(usuarioContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usuarioContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(usuarioContadorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareContador', () => {
      it('Should forward to contadorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(contadorService, 'compareContador');
        comp.compareContador(entity, entity2);
        expect(contadorService.compareContador).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAdministrador', () => {
      it('Should forward to administradorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(administradorService, 'compareAdministrador');
        comp.compareAdministrador(entity, entity2);
        expect(administradorService.compareAdministrador).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
