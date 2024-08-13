import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IUsuarioContador } from 'app/entities/usuario-contador/usuario-contador.model';
import { UsuarioContadorService } from 'app/entities/usuario-contador/service/usuario-contador.service';
import { IPermisao } from 'app/entities/permisao/permisao.model';
import { PermisaoService } from 'app/entities/permisao/service/permisao.service';
import { IGrupoAcessoEmpresa } from 'app/entities/grupo-acesso-empresa/grupo-acesso-empresa.model';
import { GrupoAcessoEmpresaService } from 'app/entities/grupo-acesso-empresa/service/grupo-acesso-empresa.service';
import { IGrupoAcessoEmpresaUsuarioContador } from '../grupo-acesso-empresa-usuario-contador.model';
import { GrupoAcessoEmpresaUsuarioContadorService } from '../service/grupo-acesso-empresa-usuario-contador.service';
import { GrupoAcessoEmpresaUsuarioContadorFormService } from './grupo-acesso-empresa-usuario-contador-form.service';

import { GrupoAcessoEmpresaUsuarioContadorUpdateComponent } from './grupo-acesso-empresa-usuario-contador-update.component';

describe('GrupoAcessoEmpresaUsuarioContador Management Update Component', () => {
  let comp: GrupoAcessoEmpresaUsuarioContadorUpdateComponent;
  let fixture: ComponentFixture<GrupoAcessoEmpresaUsuarioContadorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let grupoAcessoEmpresaUsuarioContadorFormService: GrupoAcessoEmpresaUsuarioContadorFormService;
  let grupoAcessoEmpresaUsuarioContadorService: GrupoAcessoEmpresaUsuarioContadorService;
  let usuarioContadorService: UsuarioContadorService;
  let permisaoService: PermisaoService;
  let grupoAcessoEmpresaService: GrupoAcessoEmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [GrupoAcessoEmpresaUsuarioContadorUpdateComponent],
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
      .overrideTemplate(GrupoAcessoEmpresaUsuarioContadorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GrupoAcessoEmpresaUsuarioContadorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    grupoAcessoEmpresaUsuarioContadorFormService = TestBed.inject(GrupoAcessoEmpresaUsuarioContadorFormService);
    grupoAcessoEmpresaUsuarioContadorService = TestBed.inject(GrupoAcessoEmpresaUsuarioContadorService);
    usuarioContadorService = TestBed.inject(UsuarioContadorService);
    permisaoService = TestBed.inject(PermisaoService);
    grupoAcessoEmpresaService = TestBed.inject(GrupoAcessoEmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call UsuarioContador query and add missing value', () => {
      const grupoAcessoEmpresaUsuarioContador: IGrupoAcessoEmpresaUsuarioContador = { id: 456 };
      const usuarioContador: IUsuarioContador = { id: 28090 };
      grupoAcessoEmpresaUsuarioContador.usuarioContador = usuarioContador;

      const usuarioContadorCollection: IUsuarioContador[] = [{ id: 32350 }];
      jest.spyOn(usuarioContadorService, 'query').mockReturnValue(of(new HttpResponse({ body: usuarioContadorCollection })));
      const additionalUsuarioContadors = [usuarioContador];
      const expectedCollection: IUsuarioContador[] = [...additionalUsuarioContadors, ...usuarioContadorCollection];
      jest.spyOn(usuarioContadorService, 'addUsuarioContadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ grupoAcessoEmpresaUsuarioContador });
      comp.ngOnInit();

      expect(usuarioContadorService.query).toHaveBeenCalled();
      expect(usuarioContadorService.addUsuarioContadorToCollectionIfMissing).toHaveBeenCalledWith(
        usuarioContadorCollection,
        ...additionalUsuarioContadors.map(expect.objectContaining),
      );
      expect(comp.usuarioContadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Permisao query and add missing value', () => {
      const grupoAcessoEmpresaUsuarioContador: IGrupoAcessoEmpresaUsuarioContador = { id: 456 };
      const permisao: IPermisao = { id: 30496 };
      grupoAcessoEmpresaUsuarioContador.permisao = permisao;

      const permisaoCollection: IPermisao[] = [{ id: 917 }];
      jest.spyOn(permisaoService, 'query').mockReturnValue(of(new HttpResponse({ body: permisaoCollection })));
      const additionalPermisaos = [permisao];
      const expectedCollection: IPermisao[] = [...additionalPermisaos, ...permisaoCollection];
      jest.spyOn(permisaoService, 'addPermisaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ grupoAcessoEmpresaUsuarioContador });
      comp.ngOnInit();

      expect(permisaoService.query).toHaveBeenCalled();
      expect(permisaoService.addPermisaoToCollectionIfMissing).toHaveBeenCalledWith(
        permisaoCollection,
        ...additionalPermisaos.map(expect.objectContaining),
      );
      expect(comp.permisaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call GrupoAcessoEmpresa query and add missing value', () => {
      const grupoAcessoEmpresaUsuarioContador: IGrupoAcessoEmpresaUsuarioContador = { id: 456 };
      const grupoAcessoEmpresa: IGrupoAcessoEmpresa = { id: 16991 };
      grupoAcessoEmpresaUsuarioContador.grupoAcessoEmpresa = grupoAcessoEmpresa;

      const grupoAcessoEmpresaCollection: IGrupoAcessoEmpresa[] = [{ id: 25466 }];
      jest.spyOn(grupoAcessoEmpresaService, 'query').mockReturnValue(of(new HttpResponse({ body: grupoAcessoEmpresaCollection })));
      const additionalGrupoAcessoEmpresas = [grupoAcessoEmpresa];
      const expectedCollection: IGrupoAcessoEmpresa[] = [...additionalGrupoAcessoEmpresas, ...grupoAcessoEmpresaCollection];
      jest.spyOn(grupoAcessoEmpresaService, 'addGrupoAcessoEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ grupoAcessoEmpresaUsuarioContador });
      comp.ngOnInit();

      expect(grupoAcessoEmpresaService.query).toHaveBeenCalled();
      expect(grupoAcessoEmpresaService.addGrupoAcessoEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        grupoAcessoEmpresaCollection,
        ...additionalGrupoAcessoEmpresas.map(expect.objectContaining),
      );
      expect(comp.grupoAcessoEmpresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const grupoAcessoEmpresaUsuarioContador: IGrupoAcessoEmpresaUsuarioContador = { id: 456 };
      const usuarioContador: IUsuarioContador = { id: 20897 };
      grupoAcessoEmpresaUsuarioContador.usuarioContador = usuarioContador;
      const permisao: IPermisao = { id: 11320 };
      grupoAcessoEmpresaUsuarioContador.permisao = permisao;
      const grupoAcessoEmpresa: IGrupoAcessoEmpresa = { id: 7979 };
      grupoAcessoEmpresaUsuarioContador.grupoAcessoEmpresa = grupoAcessoEmpresa;

      activatedRoute.data = of({ grupoAcessoEmpresaUsuarioContador });
      comp.ngOnInit();

      expect(comp.usuarioContadorsSharedCollection).toContain(usuarioContador);
      expect(comp.permisaosSharedCollection).toContain(permisao);
      expect(comp.grupoAcessoEmpresasSharedCollection).toContain(grupoAcessoEmpresa);
      expect(comp.grupoAcessoEmpresaUsuarioContador).toEqual(grupoAcessoEmpresaUsuarioContador);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGrupoAcessoEmpresaUsuarioContador>>();
      const grupoAcessoEmpresaUsuarioContador = { id: 123 };
      jest
        .spyOn(grupoAcessoEmpresaUsuarioContadorFormService, 'getGrupoAcessoEmpresaUsuarioContador')
        .mockReturnValue(grupoAcessoEmpresaUsuarioContador);
      jest.spyOn(grupoAcessoEmpresaUsuarioContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoAcessoEmpresaUsuarioContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: grupoAcessoEmpresaUsuarioContador }));
      saveSubject.complete();

      // THEN
      expect(grupoAcessoEmpresaUsuarioContadorFormService.getGrupoAcessoEmpresaUsuarioContador).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(grupoAcessoEmpresaUsuarioContadorService.update).toHaveBeenCalledWith(
        expect.objectContaining(grupoAcessoEmpresaUsuarioContador),
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGrupoAcessoEmpresaUsuarioContador>>();
      const grupoAcessoEmpresaUsuarioContador = { id: 123 };
      jest.spyOn(grupoAcessoEmpresaUsuarioContadorFormService, 'getGrupoAcessoEmpresaUsuarioContador').mockReturnValue({ id: null });
      jest.spyOn(grupoAcessoEmpresaUsuarioContadorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoAcessoEmpresaUsuarioContador: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: grupoAcessoEmpresaUsuarioContador }));
      saveSubject.complete();

      // THEN
      expect(grupoAcessoEmpresaUsuarioContadorFormService.getGrupoAcessoEmpresaUsuarioContador).toHaveBeenCalled();
      expect(grupoAcessoEmpresaUsuarioContadorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGrupoAcessoEmpresaUsuarioContador>>();
      const grupoAcessoEmpresaUsuarioContador = { id: 123 };
      jest.spyOn(grupoAcessoEmpresaUsuarioContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoAcessoEmpresaUsuarioContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(grupoAcessoEmpresaUsuarioContadorService.update).toHaveBeenCalled();
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

    describe('comparePermisao', () => {
      it('Should forward to permisaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(permisaoService, 'comparePermisao');
        comp.comparePermisao(entity, entity2);
        expect(permisaoService.comparePermisao).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareGrupoAcessoEmpresa', () => {
      it('Should forward to grupoAcessoEmpresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(grupoAcessoEmpresaService, 'compareGrupoAcessoEmpresa');
        comp.compareGrupoAcessoEmpresa(entity, entity2);
        expect(grupoAcessoEmpresaService.compareGrupoAcessoEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
