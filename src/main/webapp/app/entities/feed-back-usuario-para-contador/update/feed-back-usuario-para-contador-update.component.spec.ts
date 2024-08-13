import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IUsuarioEmpresa } from 'app/entities/usuario-empresa/usuario-empresa.model';
import { UsuarioEmpresaService } from 'app/entities/usuario-empresa/service/usuario-empresa.service';
import { IUsuarioContador } from 'app/entities/usuario-contador/usuario-contador.model';
import { UsuarioContadorService } from 'app/entities/usuario-contador/service/usuario-contador.service';
import { ICriterioAvaliacaoAtor } from 'app/entities/criterio-avaliacao-ator/criterio-avaliacao-ator.model';
import { CriterioAvaliacaoAtorService } from 'app/entities/criterio-avaliacao-ator/service/criterio-avaliacao-ator.service';
import { IOrdemServico } from 'app/entities/ordem-servico/ordem-servico.model';
import { OrdemServicoService } from 'app/entities/ordem-servico/service/ordem-servico.service';
import { IFeedBackUsuarioParaContador } from '../feed-back-usuario-para-contador.model';
import { FeedBackUsuarioParaContadorService } from '../service/feed-back-usuario-para-contador.service';
import { FeedBackUsuarioParaContadorFormService } from './feed-back-usuario-para-contador-form.service';

import { FeedBackUsuarioParaContadorUpdateComponent } from './feed-back-usuario-para-contador-update.component';

describe('FeedBackUsuarioParaContador Management Update Component', () => {
  let comp: FeedBackUsuarioParaContadorUpdateComponent;
  let fixture: ComponentFixture<FeedBackUsuarioParaContadorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let feedBackUsuarioParaContadorFormService: FeedBackUsuarioParaContadorFormService;
  let feedBackUsuarioParaContadorService: FeedBackUsuarioParaContadorService;
  let usuarioEmpresaService: UsuarioEmpresaService;
  let usuarioContadorService: UsuarioContadorService;
  let criterioAvaliacaoAtorService: CriterioAvaliacaoAtorService;
  let ordemServicoService: OrdemServicoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FeedBackUsuarioParaContadorUpdateComponent],
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
      .overrideTemplate(FeedBackUsuarioParaContadorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FeedBackUsuarioParaContadorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    feedBackUsuarioParaContadorFormService = TestBed.inject(FeedBackUsuarioParaContadorFormService);
    feedBackUsuarioParaContadorService = TestBed.inject(FeedBackUsuarioParaContadorService);
    usuarioEmpresaService = TestBed.inject(UsuarioEmpresaService);
    usuarioContadorService = TestBed.inject(UsuarioContadorService);
    criterioAvaliacaoAtorService = TestBed.inject(CriterioAvaliacaoAtorService);
    ordemServicoService = TestBed.inject(OrdemServicoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call UsuarioEmpresa query and add missing value', () => {
      const feedBackUsuarioParaContador: IFeedBackUsuarioParaContador = { id: 456 };
      const usuarioEmpresa: IUsuarioEmpresa = { id: 5637 };
      feedBackUsuarioParaContador.usuarioEmpresa = usuarioEmpresa;

      const usuarioEmpresaCollection: IUsuarioEmpresa[] = [{ id: 30780 }];
      jest.spyOn(usuarioEmpresaService, 'query').mockReturnValue(of(new HttpResponse({ body: usuarioEmpresaCollection })));
      const additionalUsuarioEmpresas = [usuarioEmpresa];
      const expectedCollection: IUsuarioEmpresa[] = [...additionalUsuarioEmpresas, ...usuarioEmpresaCollection];
      jest.spyOn(usuarioEmpresaService, 'addUsuarioEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ feedBackUsuarioParaContador });
      comp.ngOnInit();

      expect(usuarioEmpresaService.query).toHaveBeenCalled();
      expect(usuarioEmpresaService.addUsuarioEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        usuarioEmpresaCollection,
        ...additionalUsuarioEmpresas.map(expect.objectContaining),
      );
      expect(comp.usuarioEmpresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call UsuarioContador query and add missing value', () => {
      const feedBackUsuarioParaContador: IFeedBackUsuarioParaContador = { id: 456 };
      const usuarioContador: IUsuarioContador = { id: 22173 };
      feedBackUsuarioParaContador.usuarioContador = usuarioContador;

      const usuarioContadorCollection: IUsuarioContador[] = [{ id: 28901 }];
      jest.spyOn(usuarioContadorService, 'query').mockReturnValue(of(new HttpResponse({ body: usuarioContadorCollection })));
      const additionalUsuarioContadors = [usuarioContador];
      const expectedCollection: IUsuarioContador[] = [...additionalUsuarioContadors, ...usuarioContadorCollection];
      jest.spyOn(usuarioContadorService, 'addUsuarioContadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ feedBackUsuarioParaContador });
      comp.ngOnInit();

      expect(usuarioContadorService.query).toHaveBeenCalled();
      expect(usuarioContadorService.addUsuarioContadorToCollectionIfMissing).toHaveBeenCalledWith(
        usuarioContadorCollection,
        ...additionalUsuarioContadors.map(expect.objectContaining),
      );
      expect(comp.usuarioContadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CriterioAvaliacaoAtor query and add missing value', () => {
      const feedBackUsuarioParaContador: IFeedBackUsuarioParaContador = { id: 456 };
      const criterioAvaliacaoAtor: ICriterioAvaliacaoAtor = { id: 24911 };
      feedBackUsuarioParaContador.criterioAvaliacaoAtor = criterioAvaliacaoAtor;

      const criterioAvaliacaoAtorCollection: ICriterioAvaliacaoAtor[] = [{ id: 20028 }];
      jest.spyOn(criterioAvaliacaoAtorService, 'query').mockReturnValue(of(new HttpResponse({ body: criterioAvaliacaoAtorCollection })));
      const additionalCriterioAvaliacaoAtors = [criterioAvaliacaoAtor];
      const expectedCollection: ICriterioAvaliacaoAtor[] = [...additionalCriterioAvaliacaoAtors, ...criterioAvaliacaoAtorCollection];
      jest.spyOn(criterioAvaliacaoAtorService, 'addCriterioAvaliacaoAtorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ feedBackUsuarioParaContador });
      comp.ngOnInit();

      expect(criterioAvaliacaoAtorService.query).toHaveBeenCalled();
      expect(criterioAvaliacaoAtorService.addCriterioAvaliacaoAtorToCollectionIfMissing).toHaveBeenCalledWith(
        criterioAvaliacaoAtorCollection,
        ...additionalCriterioAvaliacaoAtors.map(expect.objectContaining),
      );
      expect(comp.criterioAvaliacaoAtorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call OrdemServico query and add missing value', () => {
      const feedBackUsuarioParaContador: IFeedBackUsuarioParaContador = { id: 456 };
      const ordemServico: IOrdemServico = { id: 7141 };
      feedBackUsuarioParaContador.ordemServico = ordemServico;

      const ordemServicoCollection: IOrdemServico[] = [{ id: 12895 }];
      jest.spyOn(ordemServicoService, 'query').mockReturnValue(of(new HttpResponse({ body: ordemServicoCollection })));
      const additionalOrdemServicos = [ordemServico];
      const expectedCollection: IOrdemServico[] = [...additionalOrdemServicos, ...ordemServicoCollection];
      jest.spyOn(ordemServicoService, 'addOrdemServicoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ feedBackUsuarioParaContador });
      comp.ngOnInit();

      expect(ordemServicoService.query).toHaveBeenCalled();
      expect(ordemServicoService.addOrdemServicoToCollectionIfMissing).toHaveBeenCalledWith(
        ordemServicoCollection,
        ...additionalOrdemServicos.map(expect.objectContaining),
      );
      expect(comp.ordemServicosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const feedBackUsuarioParaContador: IFeedBackUsuarioParaContador = { id: 456 };
      const usuarioEmpresa: IUsuarioEmpresa = { id: 14554 };
      feedBackUsuarioParaContador.usuarioEmpresa = usuarioEmpresa;
      const usuarioContador: IUsuarioContador = { id: 22042 };
      feedBackUsuarioParaContador.usuarioContador = usuarioContador;
      const criterioAvaliacaoAtor: ICriterioAvaliacaoAtor = { id: 16843 };
      feedBackUsuarioParaContador.criterioAvaliacaoAtor = criterioAvaliacaoAtor;
      const ordemServico: IOrdemServico = { id: 9514 };
      feedBackUsuarioParaContador.ordemServico = ordemServico;

      activatedRoute.data = of({ feedBackUsuarioParaContador });
      comp.ngOnInit();

      expect(comp.usuarioEmpresasSharedCollection).toContain(usuarioEmpresa);
      expect(comp.usuarioContadorsSharedCollection).toContain(usuarioContador);
      expect(comp.criterioAvaliacaoAtorsSharedCollection).toContain(criterioAvaliacaoAtor);
      expect(comp.ordemServicosSharedCollection).toContain(ordemServico);
      expect(comp.feedBackUsuarioParaContador).toEqual(feedBackUsuarioParaContador);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFeedBackUsuarioParaContador>>();
      const feedBackUsuarioParaContador = { id: 123 };
      jest.spyOn(feedBackUsuarioParaContadorFormService, 'getFeedBackUsuarioParaContador').mockReturnValue(feedBackUsuarioParaContador);
      jest.spyOn(feedBackUsuarioParaContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ feedBackUsuarioParaContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: feedBackUsuarioParaContador }));
      saveSubject.complete();

      // THEN
      expect(feedBackUsuarioParaContadorFormService.getFeedBackUsuarioParaContador).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(feedBackUsuarioParaContadorService.update).toHaveBeenCalledWith(expect.objectContaining(feedBackUsuarioParaContador));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFeedBackUsuarioParaContador>>();
      const feedBackUsuarioParaContador = { id: 123 };
      jest.spyOn(feedBackUsuarioParaContadorFormService, 'getFeedBackUsuarioParaContador').mockReturnValue({ id: null });
      jest.spyOn(feedBackUsuarioParaContadorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ feedBackUsuarioParaContador: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: feedBackUsuarioParaContador }));
      saveSubject.complete();

      // THEN
      expect(feedBackUsuarioParaContadorFormService.getFeedBackUsuarioParaContador).toHaveBeenCalled();
      expect(feedBackUsuarioParaContadorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFeedBackUsuarioParaContador>>();
      const feedBackUsuarioParaContador = { id: 123 };
      jest.spyOn(feedBackUsuarioParaContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ feedBackUsuarioParaContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(feedBackUsuarioParaContadorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUsuarioEmpresa', () => {
      it('Should forward to usuarioEmpresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(usuarioEmpresaService, 'compareUsuarioEmpresa');
        comp.compareUsuarioEmpresa(entity, entity2);
        expect(usuarioEmpresaService.compareUsuarioEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareUsuarioContador', () => {
      it('Should forward to usuarioContadorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(usuarioContadorService, 'compareUsuarioContador');
        comp.compareUsuarioContador(entity, entity2);
        expect(usuarioContadorService.compareUsuarioContador).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCriterioAvaliacaoAtor', () => {
      it('Should forward to criterioAvaliacaoAtorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(criterioAvaliacaoAtorService, 'compareCriterioAvaliacaoAtor');
        comp.compareCriterioAvaliacaoAtor(entity, entity2);
        expect(criterioAvaliacaoAtorService.compareCriterioAvaliacaoAtor).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareOrdemServico', () => {
      it('Should forward to ordemServicoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(ordemServicoService, 'compareOrdemServico');
        comp.compareOrdemServico(entity, entity2);
        expect(ordemServicoService.compareOrdemServico).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
