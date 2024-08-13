import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IFuncionalidade } from 'app/entities/funcionalidade/funcionalidade.model';
import { FuncionalidadeService } from 'app/entities/funcionalidade/service/funcionalidade.service';
import { IGrupoAcessoPadrao } from 'app/entities/grupo-acesso-padrao/grupo-acesso-padrao.model';
import { GrupoAcessoPadraoService } from 'app/entities/grupo-acesso-padrao/service/grupo-acesso-padrao.service';
import { IPermisao } from 'app/entities/permisao/permisao.model';
import { PermisaoService } from 'app/entities/permisao/service/permisao.service';
import { IFuncionalidadeGrupoAcessoPadrao } from '../funcionalidade-grupo-acesso-padrao.model';
import { FuncionalidadeGrupoAcessoPadraoService } from '../service/funcionalidade-grupo-acesso-padrao.service';
import { FuncionalidadeGrupoAcessoPadraoFormService } from './funcionalidade-grupo-acesso-padrao-form.service';

import { FuncionalidadeGrupoAcessoPadraoUpdateComponent } from './funcionalidade-grupo-acesso-padrao-update.component';

describe('FuncionalidadeGrupoAcessoPadrao Management Update Component', () => {
  let comp: FuncionalidadeGrupoAcessoPadraoUpdateComponent;
  let fixture: ComponentFixture<FuncionalidadeGrupoAcessoPadraoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let funcionalidadeGrupoAcessoPadraoFormService: FuncionalidadeGrupoAcessoPadraoFormService;
  let funcionalidadeGrupoAcessoPadraoService: FuncionalidadeGrupoAcessoPadraoService;
  let funcionalidadeService: FuncionalidadeService;
  let grupoAcessoPadraoService: GrupoAcessoPadraoService;
  let permisaoService: PermisaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FuncionalidadeGrupoAcessoPadraoUpdateComponent],
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
      .overrideTemplate(FuncionalidadeGrupoAcessoPadraoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FuncionalidadeGrupoAcessoPadraoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    funcionalidadeGrupoAcessoPadraoFormService = TestBed.inject(FuncionalidadeGrupoAcessoPadraoFormService);
    funcionalidadeGrupoAcessoPadraoService = TestBed.inject(FuncionalidadeGrupoAcessoPadraoService);
    funcionalidadeService = TestBed.inject(FuncionalidadeService);
    grupoAcessoPadraoService = TestBed.inject(GrupoAcessoPadraoService);
    permisaoService = TestBed.inject(PermisaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Funcionalidade query and add missing value', () => {
      const funcionalidadeGrupoAcessoPadrao: IFuncionalidadeGrupoAcessoPadrao = { id: 456 };
      const funcionalidade: IFuncionalidade = { id: 9509 };
      funcionalidadeGrupoAcessoPadrao.funcionalidade = funcionalidade;

      const funcionalidadeCollection: IFuncionalidade[] = [{ id: 18692 }];
      jest.spyOn(funcionalidadeService, 'query').mockReturnValue(of(new HttpResponse({ body: funcionalidadeCollection })));
      const additionalFuncionalidades = [funcionalidade];
      const expectedCollection: IFuncionalidade[] = [...additionalFuncionalidades, ...funcionalidadeCollection];
      jest.spyOn(funcionalidadeService, 'addFuncionalidadeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ funcionalidadeGrupoAcessoPadrao });
      comp.ngOnInit();

      expect(funcionalidadeService.query).toHaveBeenCalled();
      expect(funcionalidadeService.addFuncionalidadeToCollectionIfMissing).toHaveBeenCalledWith(
        funcionalidadeCollection,
        ...additionalFuncionalidades.map(expect.objectContaining),
      );
      expect(comp.funcionalidadesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call GrupoAcessoPadrao query and add missing value', () => {
      const funcionalidadeGrupoAcessoPadrao: IFuncionalidadeGrupoAcessoPadrao = { id: 456 };
      const grupoAcessoPadrao: IGrupoAcessoPadrao = { id: 24701 };
      funcionalidadeGrupoAcessoPadrao.grupoAcessoPadrao = grupoAcessoPadrao;

      const grupoAcessoPadraoCollection: IGrupoAcessoPadrao[] = [{ id: 11239 }];
      jest.spyOn(grupoAcessoPadraoService, 'query').mockReturnValue(of(new HttpResponse({ body: grupoAcessoPadraoCollection })));
      const additionalGrupoAcessoPadraos = [grupoAcessoPadrao];
      const expectedCollection: IGrupoAcessoPadrao[] = [...additionalGrupoAcessoPadraos, ...grupoAcessoPadraoCollection];
      jest.spyOn(grupoAcessoPadraoService, 'addGrupoAcessoPadraoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ funcionalidadeGrupoAcessoPadrao });
      comp.ngOnInit();

      expect(grupoAcessoPadraoService.query).toHaveBeenCalled();
      expect(grupoAcessoPadraoService.addGrupoAcessoPadraoToCollectionIfMissing).toHaveBeenCalledWith(
        grupoAcessoPadraoCollection,
        ...additionalGrupoAcessoPadraos.map(expect.objectContaining),
      );
      expect(comp.grupoAcessoPadraosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Permisao query and add missing value', () => {
      const funcionalidadeGrupoAcessoPadrao: IFuncionalidadeGrupoAcessoPadrao = { id: 456 };
      const permisao: IPermisao = { id: 14596 };
      funcionalidadeGrupoAcessoPadrao.permisao = permisao;

      const permisaoCollection: IPermisao[] = [{ id: 21525 }];
      jest.spyOn(permisaoService, 'query').mockReturnValue(of(new HttpResponse({ body: permisaoCollection })));
      const additionalPermisaos = [permisao];
      const expectedCollection: IPermisao[] = [...additionalPermisaos, ...permisaoCollection];
      jest.spyOn(permisaoService, 'addPermisaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ funcionalidadeGrupoAcessoPadrao });
      comp.ngOnInit();

      expect(permisaoService.query).toHaveBeenCalled();
      expect(permisaoService.addPermisaoToCollectionIfMissing).toHaveBeenCalledWith(
        permisaoCollection,
        ...additionalPermisaos.map(expect.objectContaining),
      );
      expect(comp.permisaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const funcionalidadeGrupoAcessoPadrao: IFuncionalidadeGrupoAcessoPadrao = { id: 456 };
      const funcionalidade: IFuncionalidade = { id: 12227 };
      funcionalidadeGrupoAcessoPadrao.funcionalidade = funcionalidade;
      const grupoAcessoPadrao: IGrupoAcessoPadrao = { id: 17919 };
      funcionalidadeGrupoAcessoPadrao.grupoAcessoPadrao = grupoAcessoPadrao;
      const permisao: IPermisao = { id: 29800 };
      funcionalidadeGrupoAcessoPadrao.permisao = permisao;

      activatedRoute.data = of({ funcionalidadeGrupoAcessoPadrao });
      comp.ngOnInit();

      expect(comp.funcionalidadesSharedCollection).toContain(funcionalidade);
      expect(comp.grupoAcessoPadraosSharedCollection).toContain(grupoAcessoPadrao);
      expect(comp.permisaosSharedCollection).toContain(permisao);
      expect(comp.funcionalidadeGrupoAcessoPadrao).toEqual(funcionalidadeGrupoAcessoPadrao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuncionalidadeGrupoAcessoPadrao>>();
      const funcionalidadeGrupoAcessoPadrao = { id: 123 };
      jest
        .spyOn(funcionalidadeGrupoAcessoPadraoFormService, 'getFuncionalidadeGrupoAcessoPadrao')
        .mockReturnValue(funcionalidadeGrupoAcessoPadrao);
      jest.spyOn(funcionalidadeGrupoAcessoPadraoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ funcionalidadeGrupoAcessoPadrao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: funcionalidadeGrupoAcessoPadrao }));
      saveSubject.complete();

      // THEN
      expect(funcionalidadeGrupoAcessoPadraoFormService.getFuncionalidadeGrupoAcessoPadrao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(funcionalidadeGrupoAcessoPadraoService.update).toHaveBeenCalledWith(expect.objectContaining(funcionalidadeGrupoAcessoPadrao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuncionalidadeGrupoAcessoPadrao>>();
      const funcionalidadeGrupoAcessoPadrao = { id: 123 };
      jest.spyOn(funcionalidadeGrupoAcessoPadraoFormService, 'getFuncionalidadeGrupoAcessoPadrao').mockReturnValue({ id: null });
      jest.spyOn(funcionalidadeGrupoAcessoPadraoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ funcionalidadeGrupoAcessoPadrao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: funcionalidadeGrupoAcessoPadrao }));
      saveSubject.complete();

      // THEN
      expect(funcionalidadeGrupoAcessoPadraoFormService.getFuncionalidadeGrupoAcessoPadrao).toHaveBeenCalled();
      expect(funcionalidadeGrupoAcessoPadraoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuncionalidadeGrupoAcessoPadrao>>();
      const funcionalidadeGrupoAcessoPadrao = { id: 123 };
      jest.spyOn(funcionalidadeGrupoAcessoPadraoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ funcionalidadeGrupoAcessoPadrao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(funcionalidadeGrupoAcessoPadraoService.update).toHaveBeenCalled();
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

    describe('compareGrupoAcessoPadrao', () => {
      it('Should forward to grupoAcessoPadraoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(grupoAcessoPadraoService, 'compareGrupoAcessoPadrao');
        comp.compareGrupoAcessoPadrao(entity, entity2);
        expect(grupoAcessoPadraoService.compareGrupoAcessoPadrao).toHaveBeenCalledWith(entity, entity2);
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
