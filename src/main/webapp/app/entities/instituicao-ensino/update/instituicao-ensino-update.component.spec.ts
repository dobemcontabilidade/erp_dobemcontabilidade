import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';
import { InstituicaoEnsinoService } from '../service/instituicao-ensino.service';
import { IInstituicaoEnsino } from '../instituicao-ensino.model';
import { InstituicaoEnsinoFormService } from './instituicao-ensino-form.service';

import { InstituicaoEnsinoUpdateComponent } from './instituicao-ensino-update.component';

describe('InstituicaoEnsino Management Update Component', () => {
  let comp: InstituicaoEnsinoUpdateComponent;
  let fixture: ComponentFixture<InstituicaoEnsinoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let instituicaoEnsinoFormService: InstituicaoEnsinoFormService;
  let instituicaoEnsinoService: InstituicaoEnsinoService;
  let cidadeService: CidadeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [InstituicaoEnsinoUpdateComponent],
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
      .overrideTemplate(InstituicaoEnsinoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InstituicaoEnsinoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    instituicaoEnsinoFormService = TestBed.inject(InstituicaoEnsinoFormService);
    instituicaoEnsinoService = TestBed.inject(InstituicaoEnsinoService);
    cidadeService = TestBed.inject(CidadeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Cidade query and add missing value', () => {
      const instituicaoEnsino: IInstituicaoEnsino = { id: 456 };
      const cidade: ICidade = { id: 20312 };
      instituicaoEnsino.cidade = cidade;

      const cidadeCollection: ICidade[] = [{ id: 10808 }];
      jest.spyOn(cidadeService, 'query').mockReturnValue(of(new HttpResponse({ body: cidadeCollection })));
      const additionalCidades = [cidade];
      const expectedCollection: ICidade[] = [...additionalCidades, ...cidadeCollection];
      jest.spyOn(cidadeService, 'addCidadeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ instituicaoEnsino });
      comp.ngOnInit();

      expect(cidadeService.query).toHaveBeenCalled();
      expect(cidadeService.addCidadeToCollectionIfMissing).toHaveBeenCalledWith(
        cidadeCollection,
        ...additionalCidades.map(expect.objectContaining),
      );
      expect(comp.cidadesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const instituicaoEnsino: IInstituicaoEnsino = { id: 456 };
      const cidade: ICidade = { id: 11931 };
      instituicaoEnsino.cidade = cidade;

      activatedRoute.data = of({ instituicaoEnsino });
      comp.ngOnInit();

      expect(comp.cidadesSharedCollection).toContain(cidade);
      expect(comp.instituicaoEnsino).toEqual(instituicaoEnsino);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInstituicaoEnsino>>();
      const instituicaoEnsino = { id: 123 };
      jest.spyOn(instituicaoEnsinoFormService, 'getInstituicaoEnsino').mockReturnValue(instituicaoEnsino);
      jest.spyOn(instituicaoEnsinoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ instituicaoEnsino });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: instituicaoEnsino }));
      saveSubject.complete();

      // THEN
      expect(instituicaoEnsinoFormService.getInstituicaoEnsino).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(instituicaoEnsinoService.update).toHaveBeenCalledWith(expect.objectContaining(instituicaoEnsino));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInstituicaoEnsino>>();
      const instituicaoEnsino = { id: 123 };
      jest.spyOn(instituicaoEnsinoFormService, 'getInstituicaoEnsino').mockReturnValue({ id: null });
      jest.spyOn(instituicaoEnsinoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ instituicaoEnsino: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: instituicaoEnsino }));
      saveSubject.complete();

      // THEN
      expect(instituicaoEnsinoFormService.getInstituicaoEnsino).toHaveBeenCalled();
      expect(instituicaoEnsinoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInstituicaoEnsino>>();
      const instituicaoEnsino = { id: 123 };
      jest.spyOn(instituicaoEnsinoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ instituicaoEnsino });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(instituicaoEnsinoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCidade', () => {
      it('Should forward to cidadeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cidadeService, 'compareCidade');
        comp.compareCidade(entity, entity2);
        expect(cidadeService.compareCidade).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
