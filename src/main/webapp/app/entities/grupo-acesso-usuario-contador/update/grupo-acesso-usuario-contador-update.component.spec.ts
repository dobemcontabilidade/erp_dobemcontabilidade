import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IUsuarioContador } from 'app/entities/usuario-contador/usuario-contador.model';
import { UsuarioContadorService } from 'app/entities/usuario-contador/service/usuario-contador.service';
import { IGrupoAcessoPadrao } from 'app/entities/grupo-acesso-padrao/grupo-acesso-padrao.model';
import { GrupoAcessoPadraoService } from 'app/entities/grupo-acesso-padrao/service/grupo-acesso-padrao.service';
import { IGrupoAcessoUsuarioContador } from '../grupo-acesso-usuario-contador.model';
import { GrupoAcessoUsuarioContadorService } from '../service/grupo-acesso-usuario-contador.service';
import { GrupoAcessoUsuarioContadorFormService } from './grupo-acesso-usuario-contador-form.service';

import { GrupoAcessoUsuarioContadorUpdateComponent } from './grupo-acesso-usuario-contador-update.component';

describe('GrupoAcessoUsuarioContador Management Update Component', () => {
  let comp: GrupoAcessoUsuarioContadorUpdateComponent;
  let fixture: ComponentFixture<GrupoAcessoUsuarioContadorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let grupoAcessoUsuarioContadorFormService: GrupoAcessoUsuarioContadorFormService;
  let grupoAcessoUsuarioContadorService: GrupoAcessoUsuarioContadorService;
  let usuarioContadorService: UsuarioContadorService;
  let grupoAcessoPadraoService: GrupoAcessoPadraoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [GrupoAcessoUsuarioContadorUpdateComponent],
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
      .overrideTemplate(GrupoAcessoUsuarioContadorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GrupoAcessoUsuarioContadorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    grupoAcessoUsuarioContadorFormService = TestBed.inject(GrupoAcessoUsuarioContadorFormService);
    grupoAcessoUsuarioContadorService = TestBed.inject(GrupoAcessoUsuarioContadorService);
    usuarioContadorService = TestBed.inject(UsuarioContadorService);
    grupoAcessoPadraoService = TestBed.inject(GrupoAcessoPadraoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call UsuarioContador query and add missing value', () => {
      const grupoAcessoUsuarioContador: IGrupoAcessoUsuarioContador = { id: 456 };
      const usuarioContador: IUsuarioContador = { id: 21656 };
      grupoAcessoUsuarioContador.usuarioContador = usuarioContador;

      const usuarioContadorCollection: IUsuarioContador[] = [{ id: 2764 }];
      jest.spyOn(usuarioContadorService, 'query').mockReturnValue(of(new HttpResponse({ body: usuarioContadorCollection })));
      const additionalUsuarioContadors = [usuarioContador];
      const expectedCollection: IUsuarioContador[] = [...additionalUsuarioContadors, ...usuarioContadorCollection];
      jest.spyOn(usuarioContadorService, 'addUsuarioContadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ grupoAcessoUsuarioContador });
      comp.ngOnInit();

      expect(usuarioContadorService.query).toHaveBeenCalled();
      expect(usuarioContadorService.addUsuarioContadorToCollectionIfMissing).toHaveBeenCalledWith(
        usuarioContadorCollection,
        ...additionalUsuarioContadors.map(expect.objectContaining),
      );
      expect(comp.usuarioContadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call GrupoAcessoPadrao query and add missing value', () => {
      const grupoAcessoUsuarioContador: IGrupoAcessoUsuarioContador = { id: 456 };
      const grupoAcessoPadrao: IGrupoAcessoPadrao = { id: 15114 };
      grupoAcessoUsuarioContador.grupoAcessoPadrao = grupoAcessoPadrao;

      const grupoAcessoPadraoCollection: IGrupoAcessoPadrao[] = [{ id: 12231 }];
      jest.spyOn(grupoAcessoPadraoService, 'query').mockReturnValue(of(new HttpResponse({ body: grupoAcessoPadraoCollection })));
      const additionalGrupoAcessoPadraos = [grupoAcessoPadrao];
      const expectedCollection: IGrupoAcessoPadrao[] = [...additionalGrupoAcessoPadraos, ...grupoAcessoPadraoCollection];
      jest.spyOn(grupoAcessoPadraoService, 'addGrupoAcessoPadraoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ grupoAcessoUsuarioContador });
      comp.ngOnInit();

      expect(grupoAcessoPadraoService.query).toHaveBeenCalled();
      expect(grupoAcessoPadraoService.addGrupoAcessoPadraoToCollectionIfMissing).toHaveBeenCalledWith(
        grupoAcessoPadraoCollection,
        ...additionalGrupoAcessoPadraos.map(expect.objectContaining),
      );
      expect(comp.grupoAcessoPadraosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const grupoAcessoUsuarioContador: IGrupoAcessoUsuarioContador = { id: 456 };
      const usuarioContador: IUsuarioContador = { id: 14482 };
      grupoAcessoUsuarioContador.usuarioContador = usuarioContador;
      const grupoAcessoPadrao: IGrupoAcessoPadrao = { id: 13171 };
      grupoAcessoUsuarioContador.grupoAcessoPadrao = grupoAcessoPadrao;

      activatedRoute.data = of({ grupoAcessoUsuarioContador });
      comp.ngOnInit();

      expect(comp.usuarioContadorsSharedCollection).toContain(usuarioContador);
      expect(comp.grupoAcessoPadraosSharedCollection).toContain(grupoAcessoPadrao);
      expect(comp.grupoAcessoUsuarioContador).toEqual(grupoAcessoUsuarioContador);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGrupoAcessoUsuarioContador>>();
      const grupoAcessoUsuarioContador = { id: 123 };
      jest.spyOn(grupoAcessoUsuarioContadorFormService, 'getGrupoAcessoUsuarioContador').mockReturnValue(grupoAcessoUsuarioContador);
      jest.spyOn(grupoAcessoUsuarioContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoAcessoUsuarioContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: grupoAcessoUsuarioContador }));
      saveSubject.complete();

      // THEN
      expect(grupoAcessoUsuarioContadorFormService.getGrupoAcessoUsuarioContador).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(grupoAcessoUsuarioContadorService.update).toHaveBeenCalledWith(expect.objectContaining(grupoAcessoUsuarioContador));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGrupoAcessoUsuarioContador>>();
      const grupoAcessoUsuarioContador = { id: 123 };
      jest.spyOn(grupoAcessoUsuarioContadorFormService, 'getGrupoAcessoUsuarioContador').mockReturnValue({ id: null });
      jest.spyOn(grupoAcessoUsuarioContadorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoAcessoUsuarioContador: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: grupoAcessoUsuarioContador }));
      saveSubject.complete();

      // THEN
      expect(grupoAcessoUsuarioContadorFormService.getGrupoAcessoUsuarioContador).toHaveBeenCalled();
      expect(grupoAcessoUsuarioContadorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGrupoAcessoUsuarioContador>>();
      const grupoAcessoUsuarioContador = { id: 123 };
      jest.spyOn(grupoAcessoUsuarioContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoAcessoUsuarioContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(grupoAcessoUsuarioContadorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUsuarioContador', () => {
      it('Should forward to usuarioContadorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(usuarioContadorService, 'compareUsuarioContador');
        comp.compareUsuarioContador(entity, entity2);
        expect(usuarioContadorService.compareUsuarioContador).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareGrupoAcessoPadrao', () => {
      it('Should forward to grupoAcessoPadraoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(grupoAcessoPadraoService, 'compareGrupoAcessoPadrao');
        comp.compareGrupoAcessoPadrao(entity, entity2);
        expect(grupoAcessoPadraoService.compareGrupoAcessoPadrao).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
