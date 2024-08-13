import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ICriterioAvaliacaoAtor } from 'app/entities/criterio-avaliacao-ator/criterio-avaliacao-ator.model';
import { CriterioAvaliacaoAtorService } from 'app/entities/criterio-avaliacao-ator/service/criterio-avaliacao-ator.service';
import { IUsuarioEmpresa } from 'app/entities/usuario-empresa/usuario-empresa.model';
import { UsuarioEmpresaService } from 'app/entities/usuario-empresa/service/usuario-empresa.service';
import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { IOrdemServico } from 'app/entities/ordem-servico/ordem-servico.model';
import { OrdemServicoService } from 'app/entities/ordem-servico/service/ordem-servico.service';
import { IFeedBackContadorParaUsuario } from '../feed-back-contador-para-usuario.model';
import { FeedBackContadorParaUsuarioService } from '../service/feed-back-contador-para-usuario.service';
import { FeedBackContadorParaUsuarioFormService } from './feed-back-contador-para-usuario-form.service';

import { FeedBackContadorParaUsuarioUpdateComponent } from './feed-back-contador-para-usuario-update.component';

describe('FeedBackContadorParaUsuario Management Update Component', () => {
  let comp: FeedBackContadorParaUsuarioUpdateComponent;
  let fixture: ComponentFixture<FeedBackContadorParaUsuarioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let feedBackContadorParaUsuarioFormService: FeedBackContadorParaUsuarioFormService;
  let feedBackContadorParaUsuarioService: FeedBackContadorParaUsuarioService;
  let criterioAvaliacaoAtorService: CriterioAvaliacaoAtorService;
  let usuarioEmpresaService: UsuarioEmpresaService;
  let contadorService: ContadorService;
  let ordemServicoService: OrdemServicoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FeedBackContadorParaUsuarioUpdateComponent],
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
      .overrideTemplate(FeedBackContadorParaUsuarioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FeedBackContadorParaUsuarioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    feedBackContadorParaUsuarioFormService = TestBed.inject(FeedBackContadorParaUsuarioFormService);
    feedBackContadorParaUsuarioService = TestBed.inject(FeedBackContadorParaUsuarioService);
    criterioAvaliacaoAtorService = TestBed.inject(CriterioAvaliacaoAtorService);
    usuarioEmpresaService = TestBed.inject(UsuarioEmpresaService);
    contadorService = TestBed.inject(ContadorService);
    ordemServicoService = TestBed.inject(OrdemServicoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CriterioAvaliacaoAtor query and add missing value', () => {
      const feedBackContadorParaUsuario: IFeedBackContadorParaUsuario = { id: 456 };
      const criterioAvaliacaoAtor: ICriterioAvaliacaoAtor = { id: 18940 };
      feedBackContadorParaUsuario.criterioAvaliacaoAtor = criterioAvaliacaoAtor;

      const criterioAvaliacaoAtorCollection: ICriterioAvaliacaoAtor[] = [{ id: 19812 }];
      jest.spyOn(criterioAvaliacaoAtorService, 'query').mockReturnValue(of(new HttpResponse({ body: criterioAvaliacaoAtorCollection })));
      const additionalCriterioAvaliacaoAtors = [criterioAvaliacaoAtor];
      const expectedCollection: ICriterioAvaliacaoAtor[] = [...additionalCriterioAvaliacaoAtors, ...criterioAvaliacaoAtorCollection];
      jest.spyOn(criterioAvaliacaoAtorService, 'addCriterioAvaliacaoAtorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ feedBackContadorParaUsuario });
      comp.ngOnInit();

      expect(criterioAvaliacaoAtorService.query).toHaveBeenCalled();
      expect(criterioAvaliacaoAtorService.addCriterioAvaliacaoAtorToCollectionIfMissing).toHaveBeenCalledWith(
        criterioAvaliacaoAtorCollection,
        ...additionalCriterioAvaliacaoAtors.map(expect.objectContaining),
      );
      expect(comp.criterioAvaliacaoAtorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call UsuarioEmpresa query and add missing value', () => {
      const feedBackContadorParaUsuario: IFeedBackContadorParaUsuario = { id: 456 };
      const usuarioEmpresa: IUsuarioEmpresa = { id: 12967 };
      feedBackContadorParaUsuario.usuarioEmpresa = usuarioEmpresa;

      const usuarioEmpresaCollection: IUsuarioEmpresa[] = [{ id: 10622 }];
      jest.spyOn(usuarioEmpresaService, 'query').mockReturnValue(of(new HttpResponse({ body: usuarioEmpresaCollection })));
      const additionalUsuarioEmpresas = [usuarioEmpresa];
      const expectedCollection: IUsuarioEmpresa[] = [...additionalUsuarioEmpresas, ...usuarioEmpresaCollection];
      jest.spyOn(usuarioEmpresaService, 'addUsuarioEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ feedBackContadorParaUsuario });
      comp.ngOnInit();

      expect(usuarioEmpresaService.query).toHaveBeenCalled();
      expect(usuarioEmpresaService.addUsuarioEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        usuarioEmpresaCollection,
        ...additionalUsuarioEmpresas.map(expect.objectContaining),
      );
      expect(comp.usuarioEmpresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Contador query and add missing value', () => {
      const feedBackContadorParaUsuario: IFeedBackContadorParaUsuario = { id: 456 };
      const contador: IContador = { id: 5786 };
      feedBackContadorParaUsuario.contador = contador;

      const contadorCollection: IContador[] = [{ id: 19154 }];
      jest.spyOn(contadorService, 'query').mockReturnValue(of(new HttpResponse({ body: contadorCollection })));
      const additionalContadors = [contador];
      const expectedCollection: IContador[] = [...additionalContadors, ...contadorCollection];
      jest.spyOn(contadorService, 'addContadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ feedBackContadorParaUsuario });
      comp.ngOnInit();

      expect(contadorService.query).toHaveBeenCalled();
      expect(contadorService.addContadorToCollectionIfMissing).toHaveBeenCalledWith(
        contadorCollection,
        ...additionalContadors.map(expect.objectContaining),
      );
      expect(comp.contadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call OrdemServico query and add missing value', () => {
      const feedBackContadorParaUsuario: IFeedBackContadorParaUsuario = { id: 456 };
      const ordemServico: IOrdemServico = { id: 13956 };
      feedBackContadorParaUsuario.ordemServico = ordemServico;

      const ordemServicoCollection: IOrdemServico[] = [{ id: 2861 }];
      jest.spyOn(ordemServicoService, 'query').mockReturnValue(of(new HttpResponse({ body: ordemServicoCollection })));
      const additionalOrdemServicos = [ordemServico];
      const expectedCollection: IOrdemServico[] = [...additionalOrdemServicos, ...ordemServicoCollection];
      jest.spyOn(ordemServicoService, 'addOrdemServicoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ feedBackContadorParaUsuario });
      comp.ngOnInit();

      expect(ordemServicoService.query).toHaveBeenCalled();
      expect(ordemServicoService.addOrdemServicoToCollectionIfMissing).toHaveBeenCalledWith(
        ordemServicoCollection,
        ...additionalOrdemServicos.map(expect.objectContaining),
      );
      expect(comp.ordemServicosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const feedBackContadorParaUsuario: IFeedBackContadorParaUsuario = { id: 456 };
      const criterioAvaliacaoAtor: ICriterioAvaliacaoAtor = { id: 14250 };
      feedBackContadorParaUsuario.criterioAvaliacaoAtor = criterioAvaliacaoAtor;
      const usuarioEmpresa: IUsuarioEmpresa = { id: 1599 };
      feedBackContadorParaUsuario.usuarioEmpresa = usuarioEmpresa;
      const contador: IContador = { id: 5638 };
      feedBackContadorParaUsuario.contador = contador;
      const ordemServico: IOrdemServico = { id: 19229 };
      feedBackContadorParaUsuario.ordemServico = ordemServico;

      activatedRoute.data = of({ feedBackContadorParaUsuario });
      comp.ngOnInit();

      expect(comp.criterioAvaliacaoAtorsSharedCollection).toContain(criterioAvaliacaoAtor);
      expect(comp.usuarioEmpresasSharedCollection).toContain(usuarioEmpresa);
      expect(comp.contadorsSharedCollection).toContain(contador);
      expect(comp.ordemServicosSharedCollection).toContain(ordemServico);
      expect(comp.feedBackContadorParaUsuario).toEqual(feedBackContadorParaUsuario);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFeedBackContadorParaUsuario>>();
      const feedBackContadorParaUsuario = { id: 123 };
      jest.spyOn(feedBackContadorParaUsuarioFormService, 'getFeedBackContadorParaUsuario').mockReturnValue(feedBackContadorParaUsuario);
      jest.spyOn(feedBackContadorParaUsuarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ feedBackContadorParaUsuario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: feedBackContadorParaUsuario }));
      saveSubject.complete();

      // THEN
      expect(feedBackContadorParaUsuarioFormService.getFeedBackContadorParaUsuario).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(feedBackContadorParaUsuarioService.update).toHaveBeenCalledWith(expect.objectContaining(feedBackContadorParaUsuario));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFeedBackContadorParaUsuario>>();
      const feedBackContadorParaUsuario = { id: 123 };
      jest.spyOn(feedBackContadorParaUsuarioFormService, 'getFeedBackContadorParaUsuario').mockReturnValue({ id: null });
      jest.spyOn(feedBackContadorParaUsuarioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ feedBackContadorParaUsuario: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: feedBackContadorParaUsuario }));
      saveSubject.complete();

      // THEN
      expect(feedBackContadorParaUsuarioFormService.getFeedBackContadorParaUsuario).toHaveBeenCalled();
      expect(feedBackContadorParaUsuarioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFeedBackContadorParaUsuario>>();
      const feedBackContadorParaUsuario = { id: 123 };
      jest.spyOn(feedBackContadorParaUsuarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ feedBackContadorParaUsuario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(feedBackContadorParaUsuarioService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCriterioAvaliacaoAtor', () => {
      it('Should forward to criterioAvaliacaoAtorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(criterioAvaliacaoAtorService, 'compareCriterioAvaliacaoAtor');
        comp.compareCriterioAvaliacaoAtor(entity, entity2);
        expect(criterioAvaliacaoAtorService.compareCriterioAvaliacaoAtor).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareUsuarioEmpresa', () => {
      it('Should forward to usuarioEmpresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(usuarioEmpresaService, 'compareUsuarioEmpresa');
        comp.compareUsuarioEmpresa(entity, entity2);
        expect(usuarioEmpresaService.compareUsuarioEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareContador', () => {
      it('Should forward to contadorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(contadorService, 'compareContador');
        comp.compareContador(entity, entity2);
        expect(contadorService.compareContador).toHaveBeenCalledWith(entity, entity2);
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
