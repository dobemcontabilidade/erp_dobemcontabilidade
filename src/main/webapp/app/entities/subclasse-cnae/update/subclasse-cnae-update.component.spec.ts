import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IClasseCnae } from 'app/entities/classe-cnae/classe-cnae.model';
import { ClasseCnaeService } from 'app/entities/classe-cnae/service/classe-cnae.service';
import { ISegmentoCnae } from 'app/entities/segmento-cnae/segmento-cnae.model';
import { SegmentoCnaeService } from 'app/entities/segmento-cnae/service/segmento-cnae.service';
import { ISubclasseCnae } from '../subclasse-cnae.model';
import { SubclasseCnaeService } from '../service/subclasse-cnae.service';
import { SubclasseCnaeFormService } from './subclasse-cnae-form.service';

import { SubclasseCnaeUpdateComponent } from './subclasse-cnae-update.component';

describe('SubclasseCnae Management Update Component', () => {
  let comp: SubclasseCnaeUpdateComponent;
  let fixture: ComponentFixture<SubclasseCnaeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let subclasseCnaeFormService: SubclasseCnaeFormService;
  let subclasseCnaeService: SubclasseCnaeService;
  let classeCnaeService: ClasseCnaeService;
  let segmentoCnaeService: SegmentoCnaeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SubclasseCnaeUpdateComponent],
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
      .overrideTemplate(SubclasseCnaeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SubclasseCnaeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    subclasseCnaeFormService = TestBed.inject(SubclasseCnaeFormService);
    subclasseCnaeService = TestBed.inject(SubclasseCnaeService);
    classeCnaeService = TestBed.inject(ClasseCnaeService);
    segmentoCnaeService = TestBed.inject(SegmentoCnaeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ClasseCnae query and add missing value', () => {
      const subclasseCnae: ISubclasseCnae = { id: 456 };
      const classe: IClasseCnae = { id: 17496 };
      subclasseCnae.classe = classe;

      const classeCnaeCollection: IClasseCnae[] = [{ id: 2512 }];
      jest.spyOn(classeCnaeService, 'query').mockReturnValue(of(new HttpResponse({ body: classeCnaeCollection })));
      const additionalClasseCnaes = [classe];
      const expectedCollection: IClasseCnae[] = [...additionalClasseCnaes, ...classeCnaeCollection];
      jest.spyOn(classeCnaeService, 'addClasseCnaeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ subclasseCnae });
      comp.ngOnInit();

      expect(classeCnaeService.query).toHaveBeenCalled();
      expect(classeCnaeService.addClasseCnaeToCollectionIfMissing).toHaveBeenCalledWith(
        classeCnaeCollection,
        ...additionalClasseCnaes.map(expect.objectContaining),
      );
      expect(comp.classeCnaesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SegmentoCnae query and add missing value', () => {
      const subclasseCnae: ISubclasseCnae = { id: 456 };
      const segmentoCnaes: ISegmentoCnae[] = [{ id: 12364 }];
      subclasseCnae.segmentoCnaes = segmentoCnaes;

      const segmentoCnaeCollection: ISegmentoCnae[] = [{ id: 8423 }];
      jest.spyOn(segmentoCnaeService, 'query').mockReturnValue(of(new HttpResponse({ body: segmentoCnaeCollection })));
      const additionalSegmentoCnaes = [...segmentoCnaes];
      const expectedCollection: ISegmentoCnae[] = [...additionalSegmentoCnaes, ...segmentoCnaeCollection];
      jest.spyOn(segmentoCnaeService, 'addSegmentoCnaeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ subclasseCnae });
      comp.ngOnInit();

      expect(segmentoCnaeService.query).toHaveBeenCalled();
      expect(segmentoCnaeService.addSegmentoCnaeToCollectionIfMissing).toHaveBeenCalledWith(
        segmentoCnaeCollection,
        ...additionalSegmentoCnaes.map(expect.objectContaining),
      );
      expect(comp.segmentoCnaesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const subclasseCnae: ISubclasseCnae = { id: 456 };
      const classe: IClasseCnae = { id: 7079 };
      subclasseCnae.classe = classe;
      const segmentoCnae: ISegmentoCnae = { id: 22864 };
      subclasseCnae.segmentoCnaes = [segmentoCnae];

      activatedRoute.data = of({ subclasseCnae });
      comp.ngOnInit();

      expect(comp.classeCnaesSharedCollection).toContain(classe);
      expect(comp.segmentoCnaesSharedCollection).toContain(segmentoCnae);
      expect(comp.subclasseCnae).toEqual(subclasseCnae);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubclasseCnae>>();
      const subclasseCnae = { id: 123 };
      jest.spyOn(subclasseCnaeFormService, 'getSubclasseCnae').mockReturnValue(subclasseCnae);
      jest.spyOn(subclasseCnaeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subclasseCnae });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subclasseCnae }));
      saveSubject.complete();

      // THEN
      expect(subclasseCnaeFormService.getSubclasseCnae).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(subclasseCnaeService.update).toHaveBeenCalledWith(expect.objectContaining(subclasseCnae));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubclasseCnae>>();
      const subclasseCnae = { id: 123 };
      jest.spyOn(subclasseCnaeFormService, 'getSubclasseCnae').mockReturnValue({ id: null });
      jest.spyOn(subclasseCnaeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subclasseCnae: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subclasseCnae }));
      saveSubject.complete();

      // THEN
      expect(subclasseCnaeFormService.getSubclasseCnae).toHaveBeenCalled();
      expect(subclasseCnaeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubclasseCnae>>();
      const subclasseCnae = { id: 123 };
      jest.spyOn(subclasseCnaeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subclasseCnae });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(subclasseCnaeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareClasseCnae', () => {
      it('Should forward to classeCnaeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(classeCnaeService, 'compareClasseCnae');
        comp.compareClasseCnae(entity, entity2);
        expect(classeCnaeService.compareClasseCnae).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareSegmentoCnae', () => {
      it('Should forward to segmentoCnaeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(segmentoCnaeService, 'compareSegmentoCnae');
        comp.compareSegmentoCnae(entity, entity2);
        expect(segmentoCnaeService.compareSegmentoCnae).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
