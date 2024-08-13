import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EstrangeiroDetailComponent } from './estrangeiro-detail.component';

describe('Estrangeiro Management Detail Component', () => {
  let comp: EstrangeiroDetailComponent;
  let fixture: ComponentFixture<EstrangeiroDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstrangeiroDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EstrangeiroDetailComponent,
              resolve: { estrangeiro: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EstrangeiroDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EstrangeiroDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load estrangeiro on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EstrangeiroDetailComponent);

      // THEN
      expect(instance.estrangeiro()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
