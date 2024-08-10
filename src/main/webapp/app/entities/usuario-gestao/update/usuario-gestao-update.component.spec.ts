import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IAdministrador } from 'app/entities/administrador/administrador.model';
import { AdministradorService } from 'app/entities/administrador/service/administrador.service';
import { UsuarioGestaoService } from '../service/usuario-gestao.service';
import { IUsuarioGestao } from '../usuario-gestao.model';
import { UsuarioGestaoFormService } from './usuario-gestao-form.service';

import { UsuarioGestaoUpdateComponent } from './usuario-gestao-update.component';

describe('UsuarioGestao Management Update Component', () => {
  let comp: UsuarioGestaoUpdateComponent;
  let fixture: ComponentFixture<UsuarioGestaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let usuarioGestaoFormService: UsuarioGestaoFormService;
  let usuarioGestaoService: UsuarioGestaoService;
  let administradorService: AdministradorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [UsuarioGestaoUpdateComponent],
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
      .overrideTemplate(UsuarioGestaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UsuarioGestaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    usuarioGestaoFormService = TestBed.inject(UsuarioGestaoFormService);
    usuarioGestaoService = TestBed.inject(UsuarioGestaoService);
    administradorService = TestBed.inject(AdministradorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call administrador query and add missing value', () => {
      const usuarioGestao: IUsuarioGestao = { id: 456 };
      const administrador: IAdministrador = { id: 17210 };
      usuarioGestao.administrador = administrador;

      const administradorCollection: IAdministrador[] = [{ id: 6831 }];
      jest.spyOn(administradorService, 'query').mockReturnValue(of(new HttpResponse({ body: administradorCollection })));
      const expectedCollection: IAdministrador[] = [administrador, ...administradorCollection];
      jest.spyOn(administradorService, 'addAdministradorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ usuarioGestao });
      comp.ngOnInit();

      expect(administradorService.query).toHaveBeenCalled();
      expect(administradorService.addAdministradorToCollectionIfMissing).toHaveBeenCalledWith(administradorCollection, administrador);
      expect(comp.administradorsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const usuarioGestao: IUsuarioGestao = { id: 456 };
      const administrador: IAdministrador = { id: 604 };
      usuarioGestao.administrador = administrador;

      activatedRoute.data = of({ usuarioGestao });
      comp.ngOnInit();

      expect(comp.administradorsCollection).toContain(administrador);
      expect(comp.usuarioGestao).toEqual(usuarioGestao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUsuarioGestao>>();
      const usuarioGestao = { id: 123 };
      jest.spyOn(usuarioGestaoFormService, 'getUsuarioGestao').mockReturnValue(usuarioGestao);
      jest.spyOn(usuarioGestaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usuarioGestao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: usuarioGestao }));
      saveSubject.complete();

      // THEN
      expect(usuarioGestaoFormService.getUsuarioGestao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(usuarioGestaoService.update).toHaveBeenCalledWith(expect.objectContaining(usuarioGestao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUsuarioGestao>>();
      const usuarioGestao = { id: 123 };
      jest.spyOn(usuarioGestaoFormService, 'getUsuarioGestao').mockReturnValue({ id: null });
      jest.spyOn(usuarioGestaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usuarioGestao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: usuarioGestao }));
      saveSubject.complete();

      // THEN
      expect(usuarioGestaoFormService.getUsuarioGestao).toHaveBeenCalled();
      expect(usuarioGestaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUsuarioGestao>>();
      const usuarioGestao = { id: 123 };
      jest.spyOn(usuarioGestaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usuarioGestao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(usuarioGestaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
