import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';
import { FluxoModeloService } from '../service/fluxo-modelo.service';
import { IFluxoModelo } from '../fluxo-modelo.model';
import { FluxoModeloFormService } from './fluxo-modelo-form.service';

import { FluxoModeloUpdateComponent } from './fluxo-modelo-update.component';

describe('FluxoModelo Management Update Component', () => {
  let comp: FluxoModeloUpdateComponent;
  let fixture: ComponentFixture<FluxoModeloUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fluxoModeloFormService: FluxoModeloFormService;
  let fluxoModeloService: FluxoModeloService;
  let cidadeService: CidadeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FluxoModeloUpdateComponent],
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
      .overrideTemplate(FluxoModeloUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FluxoModeloUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fluxoModeloFormService = TestBed.inject(FluxoModeloFormService);
    fluxoModeloService = TestBed.inject(FluxoModeloService);
    cidadeService = TestBed.inject(CidadeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Cidade query and add missing value', () => {
      const fluxoModelo: IFluxoModelo = { id: 456 };
      const cidade: ICidade = { id: 19326 };
      fluxoModelo.cidade = cidade;

      const cidadeCollection: ICidade[] = [{ id: 31847 }];
      jest.spyOn(cidadeService, 'query').mockReturnValue(of(new HttpResponse({ body: cidadeCollection })));
      const additionalCidades = [cidade];
      const expectedCollection: ICidade[] = [...additionalCidades, ...cidadeCollection];
      jest.spyOn(cidadeService, 'addCidadeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fluxoModelo });
      comp.ngOnInit();

      expect(cidadeService.query).toHaveBeenCalled();
      expect(cidadeService.addCidadeToCollectionIfMissing).toHaveBeenCalledWith(
        cidadeCollection,
        ...additionalCidades.map(expect.objectContaining),
      );
      expect(comp.cidadesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const fluxoModelo: IFluxoModelo = { id: 456 };
      const cidade: ICidade = { id: 31472 };
      fluxoModelo.cidade = cidade;

      activatedRoute.data = of({ fluxoModelo });
      comp.ngOnInit();

      expect(comp.cidadesSharedCollection).toContain(cidade);
      expect(comp.fluxoModelo).toEqual(fluxoModelo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFluxoModelo>>();
      const fluxoModelo = { id: 123 };
      jest.spyOn(fluxoModeloFormService, 'getFluxoModelo').mockReturnValue(fluxoModelo);
      jest.spyOn(fluxoModeloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fluxoModelo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fluxoModelo }));
      saveSubject.complete();

      // THEN
      expect(fluxoModeloFormService.getFluxoModelo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fluxoModeloService.update).toHaveBeenCalledWith(expect.objectContaining(fluxoModelo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFluxoModelo>>();
      const fluxoModelo = { id: 123 };
      jest.spyOn(fluxoModeloFormService, 'getFluxoModelo').mockReturnValue({ id: null });
      jest.spyOn(fluxoModeloService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fluxoModelo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fluxoModelo }));
      saveSubject.complete();

      // THEN
      expect(fluxoModeloFormService.getFluxoModelo).toHaveBeenCalled();
      expect(fluxoModeloService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFluxoModelo>>();
      const fluxoModelo = { id: 123 };
      jest.spyOn(fluxoModeloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fluxoModelo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fluxoModeloService.update).toHaveBeenCalled();
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
