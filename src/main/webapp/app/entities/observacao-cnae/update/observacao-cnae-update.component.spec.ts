import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ISubclasseCnae } from 'app/entities/subclasse-cnae/subclasse-cnae.model';
import { SubclasseCnaeService } from 'app/entities/subclasse-cnae/service/subclasse-cnae.service';
import { ObservacaoCnaeService } from '../service/observacao-cnae.service';
import { IObservacaoCnae } from '../observacao-cnae.model';
import { ObservacaoCnaeFormService } from './observacao-cnae-form.service';

import { ObservacaoCnaeUpdateComponent } from './observacao-cnae-update.component';

describe('ObservacaoCnae Management Update Component', () => {
  let comp: ObservacaoCnaeUpdateComponent;
  let fixture: ComponentFixture<ObservacaoCnaeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let observacaoCnaeFormService: ObservacaoCnaeFormService;
  let observacaoCnaeService: ObservacaoCnaeService;
  let subclasseCnaeService: SubclasseCnaeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ObservacaoCnaeUpdateComponent],
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
      .overrideTemplate(ObservacaoCnaeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ObservacaoCnaeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    observacaoCnaeFormService = TestBed.inject(ObservacaoCnaeFormService);
    observacaoCnaeService = TestBed.inject(ObservacaoCnaeService);
    subclasseCnaeService = TestBed.inject(SubclasseCnaeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SubclasseCnae query and add missing value', () => {
      const observacaoCnae: IObservacaoCnae = { id: 456 };
      const subclasse: ISubclasseCnae = { id: 2470 };
      observacaoCnae.subclasse = subclasse;

      const subclasseCnaeCollection: ISubclasseCnae[] = [{ id: 2066 }];
      jest.spyOn(subclasseCnaeService, 'query').mockReturnValue(of(new HttpResponse({ body: subclasseCnaeCollection })));
      const additionalSubclasseCnaes = [subclasse];
      const expectedCollection: ISubclasseCnae[] = [...additionalSubclasseCnaes, ...subclasseCnaeCollection];
      jest.spyOn(subclasseCnaeService, 'addSubclasseCnaeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ observacaoCnae });
      comp.ngOnInit();

      expect(subclasseCnaeService.query).toHaveBeenCalled();
      expect(subclasseCnaeService.addSubclasseCnaeToCollectionIfMissing).toHaveBeenCalledWith(
        subclasseCnaeCollection,
        ...additionalSubclasseCnaes.map(expect.objectContaining),
      );
      expect(comp.subclasseCnaesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const observacaoCnae: IObservacaoCnae = { id: 456 };
      const subclasse: ISubclasseCnae = { id: 32691 };
      observacaoCnae.subclasse = subclasse;

      activatedRoute.data = of({ observacaoCnae });
      comp.ngOnInit();

      expect(comp.subclasseCnaesSharedCollection).toContain(subclasse);
      expect(comp.observacaoCnae).toEqual(observacaoCnae);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IObservacaoCnae>>();
      const observacaoCnae = { id: 123 };
      jest.spyOn(observacaoCnaeFormService, 'getObservacaoCnae').mockReturnValue(observacaoCnae);
      jest.spyOn(observacaoCnaeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ observacaoCnae });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: observacaoCnae }));
      saveSubject.complete();

      // THEN
      expect(observacaoCnaeFormService.getObservacaoCnae).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(observacaoCnaeService.update).toHaveBeenCalledWith(expect.objectContaining(observacaoCnae));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IObservacaoCnae>>();
      const observacaoCnae = { id: 123 };
      jest.spyOn(observacaoCnaeFormService, 'getObservacaoCnae').mockReturnValue({ id: null });
      jest.spyOn(observacaoCnaeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ observacaoCnae: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: observacaoCnae }));
      saveSubject.complete();

      // THEN
      expect(observacaoCnaeFormService.getObservacaoCnae).toHaveBeenCalled();
      expect(observacaoCnaeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IObservacaoCnae>>();
      const observacaoCnae = { id: 123 };
      jest.spyOn(observacaoCnaeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ observacaoCnae });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(observacaoCnaeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSubclasseCnae', () => {
      it('Should forward to subclasseCnaeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(subclasseCnaeService, 'compareSubclasseCnae');
        comp.compareSubclasseCnae(entity, entity2);
        expect(subclasseCnaeService.compareSubclasseCnae).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
