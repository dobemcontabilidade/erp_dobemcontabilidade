import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IFuncionalidade } from 'app/entities/funcionalidade/funcionalidade.model';
import { FuncionalidadeService } from 'app/entities/funcionalidade/service/funcionalidade.service';
import { IGrupoAcessoEmpresa } from 'app/entities/grupo-acesso-empresa/grupo-acesso-empresa.model';
import { GrupoAcessoEmpresaService } from 'app/entities/grupo-acesso-empresa/service/grupo-acesso-empresa.service';
import { IPermisao } from 'app/entities/permisao/permisao.model';
import { PermisaoService } from 'app/entities/permisao/service/permisao.service';
import { IFuncionalidadeGrupoAcessoEmpresa } from '../funcionalidade-grupo-acesso-empresa.model';
import { FuncionalidadeGrupoAcessoEmpresaService } from '../service/funcionalidade-grupo-acesso-empresa.service';
import { FuncionalidadeGrupoAcessoEmpresaFormService } from './funcionalidade-grupo-acesso-empresa-form.service';

import { FuncionalidadeGrupoAcessoEmpresaUpdateComponent } from './funcionalidade-grupo-acesso-empresa-update.component';

describe('FuncionalidadeGrupoAcessoEmpresa Management Update Component', () => {
  let comp: FuncionalidadeGrupoAcessoEmpresaUpdateComponent;
  let fixture: ComponentFixture<FuncionalidadeGrupoAcessoEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let funcionalidadeGrupoAcessoEmpresaFormService: FuncionalidadeGrupoAcessoEmpresaFormService;
  let funcionalidadeGrupoAcessoEmpresaService: FuncionalidadeGrupoAcessoEmpresaService;
  let funcionalidadeService: FuncionalidadeService;
  let grupoAcessoEmpresaService: GrupoAcessoEmpresaService;
  let permisaoService: PermisaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FuncionalidadeGrupoAcessoEmpresaUpdateComponent],
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
      .overrideTemplate(FuncionalidadeGrupoAcessoEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FuncionalidadeGrupoAcessoEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    funcionalidadeGrupoAcessoEmpresaFormService = TestBed.inject(FuncionalidadeGrupoAcessoEmpresaFormService);
    funcionalidadeGrupoAcessoEmpresaService = TestBed.inject(FuncionalidadeGrupoAcessoEmpresaService);
    funcionalidadeService = TestBed.inject(FuncionalidadeService);
    grupoAcessoEmpresaService = TestBed.inject(GrupoAcessoEmpresaService);
    permisaoService = TestBed.inject(PermisaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Funcionalidade query and add missing value', () => {
      const funcionalidadeGrupoAcessoEmpresa: IFuncionalidadeGrupoAcessoEmpresa = { id: 456 };
      const funcionalidade: IFuncionalidade = { id: 14394 };
      funcionalidadeGrupoAcessoEmpresa.funcionalidade = funcionalidade;

      const funcionalidadeCollection: IFuncionalidade[] = [{ id: 7301 }];
      jest.spyOn(funcionalidadeService, 'query').mockReturnValue(of(new HttpResponse({ body: funcionalidadeCollection })));
      const additionalFuncionalidades = [funcionalidade];
      const expectedCollection: IFuncionalidade[] = [...additionalFuncionalidades, ...funcionalidadeCollection];
      jest.spyOn(funcionalidadeService, 'addFuncionalidadeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ funcionalidadeGrupoAcessoEmpresa });
      comp.ngOnInit();

      expect(funcionalidadeService.query).toHaveBeenCalled();
      expect(funcionalidadeService.addFuncionalidadeToCollectionIfMissing).toHaveBeenCalledWith(
        funcionalidadeCollection,
        ...additionalFuncionalidades.map(expect.objectContaining),
      );
      expect(comp.funcionalidadesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call GrupoAcessoEmpresa query and add missing value', () => {
      const funcionalidadeGrupoAcessoEmpresa: IFuncionalidadeGrupoAcessoEmpresa = { id: 456 };
      const grupoAcessoEmpresa: IGrupoAcessoEmpresa = { id: 6158 };
      funcionalidadeGrupoAcessoEmpresa.grupoAcessoEmpresa = grupoAcessoEmpresa;

      const grupoAcessoEmpresaCollection: IGrupoAcessoEmpresa[] = [{ id: 17259 }];
      jest.spyOn(grupoAcessoEmpresaService, 'query').mockReturnValue(of(new HttpResponse({ body: grupoAcessoEmpresaCollection })));
      const additionalGrupoAcessoEmpresas = [grupoAcessoEmpresa];
      const expectedCollection: IGrupoAcessoEmpresa[] = [...additionalGrupoAcessoEmpresas, ...grupoAcessoEmpresaCollection];
      jest.spyOn(grupoAcessoEmpresaService, 'addGrupoAcessoEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ funcionalidadeGrupoAcessoEmpresa });
      comp.ngOnInit();

      expect(grupoAcessoEmpresaService.query).toHaveBeenCalled();
      expect(grupoAcessoEmpresaService.addGrupoAcessoEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        grupoAcessoEmpresaCollection,
        ...additionalGrupoAcessoEmpresas.map(expect.objectContaining),
      );
      expect(comp.grupoAcessoEmpresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Permisao query and add missing value', () => {
      const funcionalidadeGrupoAcessoEmpresa: IFuncionalidadeGrupoAcessoEmpresa = { id: 456 };
      const permisao: IPermisao = { id: 8115 };
      funcionalidadeGrupoAcessoEmpresa.permisao = permisao;

      const permisaoCollection: IPermisao[] = [{ id: 3201 }];
      jest.spyOn(permisaoService, 'query').mockReturnValue(of(new HttpResponse({ body: permisaoCollection })));
      const additionalPermisaos = [permisao];
      const expectedCollection: IPermisao[] = [...additionalPermisaos, ...permisaoCollection];
      jest.spyOn(permisaoService, 'addPermisaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ funcionalidadeGrupoAcessoEmpresa });
      comp.ngOnInit();

      expect(permisaoService.query).toHaveBeenCalled();
      expect(permisaoService.addPermisaoToCollectionIfMissing).toHaveBeenCalledWith(
        permisaoCollection,
        ...additionalPermisaos.map(expect.objectContaining),
      );
      expect(comp.permisaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const funcionalidadeGrupoAcessoEmpresa: IFuncionalidadeGrupoAcessoEmpresa = { id: 456 };
      const funcionalidade: IFuncionalidade = { id: 20689 };
      funcionalidadeGrupoAcessoEmpresa.funcionalidade = funcionalidade;
      const grupoAcessoEmpresa: IGrupoAcessoEmpresa = { id: 21619 };
      funcionalidadeGrupoAcessoEmpresa.grupoAcessoEmpresa = grupoAcessoEmpresa;
      const permisao: IPermisao = { id: 17116 };
      funcionalidadeGrupoAcessoEmpresa.permisao = permisao;

      activatedRoute.data = of({ funcionalidadeGrupoAcessoEmpresa });
      comp.ngOnInit();

      expect(comp.funcionalidadesSharedCollection).toContain(funcionalidade);
      expect(comp.grupoAcessoEmpresasSharedCollection).toContain(grupoAcessoEmpresa);
      expect(comp.permisaosSharedCollection).toContain(permisao);
      expect(comp.funcionalidadeGrupoAcessoEmpresa).toEqual(funcionalidadeGrupoAcessoEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuncionalidadeGrupoAcessoEmpresa>>();
      const funcionalidadeGrupoAcessoEmpresa = { id: 123 };
      jest
        .spyOn(funcionalidadeGrupoAcessoEmpresaFormService, 'getFuncionalidadeGrupoAcessoEmpresa')
        .mockReturnValue(funcionalidadeGrupoAcessoEmpresa);
      jest.spyOn(funcionalidadeGrupoAcessoEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ funcionalidadeGrupoAcessoEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: funcionalidadeGrupoAcessoEmpresa }));
      saveSubject.complete();

      // THEN
      expect(funcionalidadeGrupoAcessoEmpresaFormService.getFuncionalidadeGrupoAcessoEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(funcionalidadeGrupoAcessoEmpresaService.update).toHaveBeenCalledWith(
        expect.objectContaining(funcionalidadeGrupoAcessoEmpresa),
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuncionalidadeGrupoAcessoEmpresa>>();
      const funcionalidadeGrupoAcessoEmpresa = { id: 123 };
      jest.spyOn(funcionalidadeGrupoAcessoEmpresaFormService, 'getFuncionalidadeGrupoAcessoEmpresa').mockReturnValue({ id: null });
      jest.spyOn(funcionalidadeGrupoAcessoEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ funcionalidadeGrupoAcessoEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: funcionalidadeGrupoAcessoEmpresa }));
      saveSubject.complete();

      // THEN
      expect(funcionalidadeGrupoAcessoEmpresaFormService.getFuncionalidadeGrupoAcessoEmpresa).toHaveBeenCalled();
      expect(funcionalidadeGrupoAcessoEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuncionalidadeGrupoAcessoEmpresa>>();
      const funcionalidadeGrupoAcessoEmpresa = { id: 123 };
      jest.spyOn(funcionalidadeGrupoAcessoEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ funcionalidadeGrupoAcessoEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(funcionalidadeGrupoAcessoEmpresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFuncionalidade', () => {
      it('Should forward to funcionalidadeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(funcionalidadeService, 'compareFuncionalidade');
        comp.compareFuncionalidade(entity, entity2);
        expect(funcionalidadeService.compareFuncionalidade).toHaveBeenCalledWith(entity, entity2);
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

    describe('comparePermisao', () => {
      it('Should forward to permisaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(permisaoService, 'comparePermisao');
        comp.comparePermisao(entity, entity2);
        expect(permisaoService.comparePermisao).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
